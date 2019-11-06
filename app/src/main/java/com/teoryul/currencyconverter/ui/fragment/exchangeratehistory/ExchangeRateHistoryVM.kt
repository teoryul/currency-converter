package com.teoryul.currencyconverter.ui.fragment.exchangeratehistory

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.event.Event
import com.teoryul.currencyconverter.network.response.date.CurrencyRateForDatesListResponse
import com.teoryul.currencyconverter.network.response.date.CurrencyRateForDatesResponse
import com.teoryul.currencyconverter.network.utils.convertCurrencyRateResponseToConversionRatePersist
import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import com.teoryul.currencyconverter.persistence.model.CurrencyPairPersist
import com.teoryul.currencyconverter.ui.fragment.BaseVM
import com.teoryul.currencyconverter.utils.*
import io.reactivex.functions.Consumer
import javax.inject.Inject

class ExchangeRateHistoryVM @Inject constructor() : BaseVM() {

    val fromCurrencyId = MutableLiveData<String>()
    val toCurrencyId = MutableLiveData<String>()
    val chartData = MutableLiveData<List<ConversionRatePersist>>().apply { value = mutableListOf() }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        queryDBConversionRates()
        fromCurrencyId.value = currencyPairDataService.pair.take(CURRENCY_CODE_LENGTH)
        toCurrencyId.value = currencyPairDataService.pair.takeLast(CURRENCY_CODE_LENGTH)
    }

    private fun queryDBConversionRates() {
        setLoadingViewProgressBarVisibility(true)
        subscribeSingle(
            dbService.conversionRateDBService.getAllConversionRatesForCurrencyPairByDateRange(
                currencyPairDataService.pair,
                DateUtils.getDateRangeStartDateAsLong(DateUtils.getFormattedCurrentTime()),
                DateUtils.getFormattedCurrentTime()
            ),
            Consumer { rates ->
                if (DateUtils.areDatesConsecutive(rates.map { it.date })) {
                    onConversionRatesLoadSuccess(rates)
                } else {
                    requestApiConversionRates()
                }
            },
            Consumer { throwable ->
                Log.e(LOG_ERROR_TAG_FAILED_QUERY, throwable.message)
                requestApiConversionRates()
            }
        )
    }

    private fun requestApiConversionRates() {
        setLoadingViewProgressBarVisibility(true)
        if (!InternetConnectivityHelper.isInternetConnectionAvailable()) {
            setErrorEvent(Event(R.string.message_dialog_no_internet_access))

            return
        }

        subscribeSingle(
            currencyApiService.getPastConversionRatesForDateRange(
                currencyPairDataService.pair,
                DateUtils.getDateRangeStartDateAsString(DateUtils.getFormattedCurrentTime()),
                DateUtils.longTimeToDateString(DateUtils.getFormattedCurrentTime())
            ),
            Consumer { rates ->
                if (rates.pairs.isEmpty() || rates.pairs[0].dates.isEmpty()) {
                    Log.e(LOG_ERROR_TAG_FAILED_REQUEST, LOG_ERROR_MESSAGE_EMPTY_RESPONSE)
                    setErrorEvent(Event(R.string.message_dialog_api_request_error))
                } else {
                    processConversionRatesApiResponse(rates)
                }
            },
            Consumer { throwable ->
                Log.e(LOG_ERROR_TAG_FAILED_REQUEST, throwable.message)
                setErrorEvent(Event(R.string.message_dialog_api_request_error))
            }
        )
    }

    private fun processConversionRatesApiResponse(rates: CurrencyRateForDatesListResponse) {
        rates.pairs.forEach { pair ->
            subscribeSingle(
                dbService.currencyPairDBService.insertCurrencyPairWithAbortStrategy(CurrencyPairPersist(pair.pair)),
                Consumer { processCurrencyPairRateApiResponse(pair) },
                Consumer { processCurrencyPairRateApiResponse(pair) }
            )
        }
    }

    private fun processCurrencyPairRateApiResponse(pair: CurrencyRateForDatesResponse) {
        val conversionRates = convertCurrencyRateResponseToConversionRatePersist(pair)
        dbService.conversionRateDBService.insertListWithReplaceStrategy(conversionRates)
        if (pair.pair == currencyPairDataService.pair) {
            onConversionRatesLoadSuccess(conversionRates)
        }
    }

    private fun onConversionRatesLoadSuccess(rates: List<ConversionRatePersist>) {
        chartData.value = rates.sorted()
        setLoadingViewProgressBarVisibility(false)
    }
}