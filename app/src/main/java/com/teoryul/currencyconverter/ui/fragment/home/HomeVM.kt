package com.teoryul.currencyconverter.ui.fragment.home

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.data.CurrencyType
import com.teoryul.currencyconverter.event.Event
import com.teoryul.currencyconverter.network.response.currency.CurrencyListResponse
import com.teoryul.currencyconverter.network.response.realtime.CurrencyRateListResponse
import com.teoryul.currencyconverter.network.utils.convertCurrencyRatesResponseToConversionRatePersist
import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import com.teoryul.currencyconverter.persistence.model.CurrencyPairPersist
import com.teoryul.currencyconverter.persistence.model.CurrencyPersist
import com.teoryul.currencyconverter.ui.fragment.BaseVM
import com.teoryul.currencyconverter.utils.*
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function3
import javax.inject.Inject

class HomeVM @Inject constructor() : BaseVM() {

    @Inject
    protected lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    val fromCurrencyTitle = MutableLiveData<Int>().apply { value = R.string.title_from_currency }
    val fromCurrencyId = MutableLiveData<String>()
    val fromCurrencySymbol = MutableLiveData<String>()
    val fromCurrencyAmount = MutableLiveData<String>()
    val toCurrencyTitle = MutableLiveData<Int>().apply { value = R.string.title_to_currency }
    val toCurrencyId = MutableLiveData<String>()
    val toCurrencySymbol = MutableLiveData<String>()
    val toCurrencyAmount = MutableLiveData<String>()

    fun setNewDestination(actionId: Int, currencyType: CurrencyType) {
        val bundle = Bundle()
        bundle.putInt(BUNDLE_KEY_CURRENCY_TYPE, currencyType.value)
        val event = Event(actionId, bundle)
        newDestinationEvent.apply { value = event }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        currencyPairDataService.pair = sharedPreferencesUtils.loadCurrencyPair()!!
        fromCurrencyAmount.value = DECIMAL_FORMAT.format(sharedPreferencesUtils.loadInputAmount())
        loadCurrencyPairData()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        if (currencyPairDataService.isPairChanged) {
            currencyPairDataService.isPairChanged = false
            loadCurrencyPairData()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        sharedPreferencesUtils.saveCurrencyPair(currencyPairDataService.pair)
        sharedPreferencesUtils.saveInputAmountPreference(fromCurrencyAmount.value)
    }

    private fun loadCurrencyPairData() =
        if (sharedPreferencesUtils.didFetchAllCurrenciesFromApi()) {
            queryDBCurrencyPairData()
        } else {
            requestApiCurrencyPairData()
        }

    private fun queryDBCurrencyPairData() {
        setLoadingViewProgressBarVisibility(true)
        subscribeSingle(
            Single.zip(
                dbService.currencyDBService.getCurrencyById(currencyPairDataService.pair.take(CURRENCY_CODE_LENGTH)),
                dbService.currencyDBService.getCurrencyById(
                    currencyPairDataService.pair.takeLast(CURRENCY_CODE_LENGTH)
                ),
                queryDBConversionRate(),
                Function3<CurrencyPersist, CurrencyPersist, Float, Unit>()
                { fromCurrency, toCurrency, conversionRate ->
                    processCurrenciesAndRatesDBQuery(fromCurrency, toCurrency, conversionRate)
                }
            ),
            Consumer {
                performConversion()
                setLoadingViewProgressBarVisibility(false)
            },
            Consumer { throwable ->
                Log.e(LOG_ERROR_TAG_FAILED_QUERY, throwable.message)
                setErrorEvent(Event(R.string.message_dialog_db_query_error))
            }
        )
    }

    private fun requestApiCurrencyPairData() {
        setLoadingViewProgressBarVisibility(true)
        if (!InternetConnectivityHelper.isInternetConnectionAvailable()) {
            setErrorEvent(Event(R.string.message_dialog_no_internet_access))

            return
        }

        subscribeSingle(
            Single.zip(
                currencyApiService.getAllCurrencies(),
                queryDBConversionRate(),
                BiFunction<CurrencyListResponse, Float, Unit>()
                { currencies, conversionRate -> processCurrenciesAndRatesResponse(currencies, conversionRate) }
            ),
            Consumer {
                performConversion()
                setLoadingViewProgressBarVisibility(false)
            },
            Consumer { throwable ->
                Log.e(LOG_ERROR_TAG_FAILED_REQUEST, throwable.message)
                setErrorEvent(Event(R.string.message_dialog_api_request_error))
            }
        )
    }

    private fun queryDBConversionRate(): Single<Float> {

        return Single.create { emitter ->
            if (CurrencyUtils.areCurrencyCodesEqual(currencyPairDataService.pair)) {
                emitter.onSuccess(DEFAULT_CONVERSION_RATE)
            } else {
                subscribeSingle(
                    dbService.conversionRateDBService.getAllConversionRatesForCurrencyPairByDate(
                        currencyPairDataService.pair, DateUtils.getFormattedCurrentTime()
                    ),
                    Consumer { conversionRatesFromDB ->
                        if (conversionRatesFromDB.isEmpty()) {
                            requestApiConversionRate(emitter)
                        } else {
                            emitter.onSuccess(retrieveConversionRateForCurrencyPair(conversionRatesFromDB))
                        }
                    },
                    Consumer { requestApiConversionRate(emitter) }
                )
            }
        }
    }

    private fun requestApiConversionRate(emitter: SingleEmitter<Float>) {
        subscribeSingle(
            currencyApiService.getRealTimeConversionRate(
                CurrencyUtils.buildCurrencyPairsQueryParam(
                    currencyPairDataService.pair,
                    CurrencyUtils.swapCurrencyPairIds(currencyPairDataService.pair)
                )
            ),
            Consumer { currencyRateListResponse ->
                if (currencyRateListResponse.rates.isEmpty()) {
                    emitter.onError(IllegalArgumentException(LOG_ERROR_MESSAGE_EMPTY_RESPONSE))
                } else {
                    emitter.onSuccess(
                        retrieveConversionRateForCurrencyPair(
                            processCurrencyRatesResponse(currencyRateListResponse)
                        )
                    )
                }
            },
            Consumer { emitter.onError(it) }
        )
    }

    private fun processCurrencyRatesResponse(currencyRatesResponse: CurrencyRateListResponse): List<ConversionRatePersist> {
        val conversionRatesPersist =
            convertCurrencyRatesResponseToConversionRatePersist(currencyRatesResponse)

        conversionRatesPersist.forEach { conversionRate ->
            subscribeSingle(
                dbService.currencyPairDBService.insertCurrencyPairWithAbortStrategy(
                    CurrencyPairPersist(conversionRate.currencyPair)
                ),
                Consumer { dbService.conversionRateDBService.insertItemWithReplaceStrategy(conversionRate) },
                Consumer {
                    // Not used
                }
            )
        }

        return conversionRatesPersist
    }

    private fun processCurrenciesAndRatesDBQuery(
        fromCurrency: CurrencyPersist, toCurrency: CurrencyPersist, conversionRate: Float
    ) {
        updateFromCurrencyData(fromCurrency)
        updateToCurrencyData(toCurrency)
        currencyPairDataService.rate = conversionRate
    }

    private fun processCurrenciesAndRatesResponse(currenciesResponse: CurrencyListResponse, conversionRate: Float) =
        if (currenciesResponse.currencies.isEmpty()) {
            Log.e(LOG_ERROR_TAG_FAILED_REQUEST, LOG_ERROR_MESSAGE_EMPTY_RESPONSE)
            setErrorEvent(Event(R.string.message_dialog_api_request_error))
        } else {
            sharedPreferencesUtils.write(SHARED_PREFERENCES_KEY_DID_FETCH_ALL_CURRENCIES_FROM_API, true)
            processCurrenciesResponse(currenciesResponse)
            currencyPairDataService.rate = conversionRate
        }

    private fun retrieveConversionRateForCurrencyPair(conversionRates: List<ConversionRatePersist>): Float {
        for (conversionRate in conversionRates) {
            if (currencyPairDataService.pair == conversionRate.currencyPair) {

                return conversionRate.rate
            }
        }

        return DEFAULT_CONVERSION_RATE
    }

    private fun processCurrenciesResponse(currenciesResponse: CurrencyListResponse) {
        currenciesResponse.currencies.forEach { currencyResponse ->
            val currencyPersist = CurrencyPersist(currencyResponse.id, currencyResponse.name, currencyResponse.symbol)

            if (currencyPairDataService.pair.startsWith(currencyResponse.id)) {
                updateFromCurrencyData(currencyPersist)
            }
            if (currencyPairDataService.pair.endsWith(currencyResponse.id)) {
                updateToCurrencyData(currencyPersist)
            }

            dbService.currencyDBService.insertItemWithIgnoreStrategy(currencyPersist)
        }
    }

    private fun updateFromCurrencyData(currencyPersist: CurrencyPersist) {
        fromCurrencyId.postValue(currencyPersist.id)
        fromCurrencySymbol.postValue(currencyPersist.symbol)
    }

    private fun updateToCurrencyData(currencyPersist: CurrencyPersist) {
        toCurrencyId.postValue(currencyPersist.id)
        toCurrencySymbol.postValue(currencyPersist.symbol)
    }

    private fun performConversion() {
        val result: Float? = fromCurrencyAmount.value?.toFloat()?.times(currencyPairDataService.rate)
        if (result != null) {
            toCurrencyAmount.value = DECIMAL_FORMAT.format(result)
        } else {
            setErrorEvent(Event(R.string.message_dialog_conversion_error))
        }
    }

    fun swapCurrencies() {
        if (CurrencyUtils.areCurrencyCodesEqual(currencyPairDataService.pair)) {

            return
        }

        swapCurrencyValues(fromCurrencyId, toCurrencyId)
        swapCurrencyValues(fromCurrencySymbol, toCurrencySymbol)
        currencyPairDataService.pair = CurrencyUtils.swapCurrencyPairIds(currencyPairDataService.pair)

        queryDBCurrencyPairData()
    }

    private fun swapCurrencyValues(firstCurrency: MutableLiveData<String>, secondCurrency: MutableLiveData<String>) {
        val tmp: String? = firstCurrency.value
        firstCurrency.value = secondCurrency.value
        secondCurrency.value = tmp
    }

    fun onNumberButtonClick(num: Int) {
        if (fromCurrencyAmount.value?.length == 1
            && fromCurrencyAmount.value.toString().startsWith(CURRENCY_INPUT_AMOUNT_ZERO)
        ) {
            fromCurrencyAmount.value = num.toString()
        } else {
            fromCurrencyAmount.value = fromCurrencyAmount.value.plus(num)
        }
        performConversion()
    }

    fun onDecimalPointButtonClick() {
        if (!fromCurrencyAmount.value.toString().contains(CURRENCY_DECIMAL_POINT)) {
            fromCurrencyAmount.value = fromCurrencyAmount.value.plus(CURRENCY_DECIMAL_POINT)
            performConversion()
        }
    }

    fun onBackspaceButtonClick() {
        if (fromCurrencyAmount.value?.length == 1) {
            fromCurrencyAmount.value = CURRENCY_INPUT_AMOUNT_ZERO
        } else {
            fromCurrencyAmount.value = fromCurrencyAmount.value?.dropLast(1)
        }
        performConversion()
    }

    fun onBackspaceButtonLongClick(view: View): Boolean {
        return if (R.id.btnBackspace == view.id) {
            fromCurrencyAmount.value = CURRENCY_INPUT_AMOUNT_ZERO
            performConversion()
            true
        } else {
            false
        }
    }
}