package com.teoryul.currencyconverter.ui.fragment.searchcurrencies

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableArrayList
import android.text.TextUtils
import android.util.Log
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.data.CurrencyType
import com.teoryul.currencyconverter.event.Event
import com.teoryul.currencyconverter.event.NavigationAction
import com.teoryul.currencyconverter.persistence.model.CurrencyPersist
import com.teoryul.currencyconverter.ui.fragment.BaseVM
import com.teoryul.currencyconverter.utils.CurrencyUtils
import com.teoryul.currencyconverter.utils.EXCEPTION_EMPTY_QUERY_RESULT
import com.teoryul.currencyconverter.utils.EXCEPTION_EMPTY_QUERY_TEXT
import com.teoryul.currencyconverter.utils.LOG_ERROR_TAG_FAILED_QUERY
import io.reactivex.Single
import io.reactivex.functions.Consumer
import javax.inject.Inject

class SearchCurrenciesVM @Inject constructor() : BaseVM() {

    private lateinit var allCurrencies: List<CurrencyPersist>
    var recViewItems = ObservableArrayList<CurrencyPersist>()
    var currencyTypeToReplace = CurrencyType.UNDEFINED

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        loadCurrencyData()
    }

    private fun loadCurrencyData() {
        setLoadingViewProgressBarVisibility(true)
        subscribeSingle(
            dbService.currencyDBService.getAllCurrencies(),
            Consumer { currencies ->
                if (currencies.isEmpty()) {
                    setLoadingViewText(R.string.loading_view_text_no_currencies_from_db)
                    setLoadingViewTextVisibility(true)
                } else {
                    processCurrenciesDBQuery(currencies)
                }
                setLoadingViewProgressBarVisibility(false)
            },
            Consumer { throwable ->
                Log.e(LOG_ERROR_TAG_FAILED_QUERY, throwable.message)
                setErrorEvent(Event(R.string.message_dialog_db_query_error))
            }
        )
    }

    private fun processCurrenciesDBQuery(currencies: List<CurrencyPersist>) {
        allCurrencies = currencies
        setRecViewItems()
    }

    private fun setRecViewItems(filteredList: List<CurrencyPersist>? = null) {
        if (!recViewItems.isEmpty()) {
            recViewItems.clear()
        }
        if (filteredList != null) {
            recViewItems.addAll(filteredList)
        } else {
            recViewItems.addAll(allCurrencies)
        }
    }

    fun onSearchQueryTextChange(text: String) {
        subscribeSingle(
            processSearchQuery(text),
            Consumer { filteredList ->
                setLoadingViewTextVisibility(false)
                setRecViewItems(filteredList.sorted())
            },
            Consumer { throwable ->
                if (EXCEPTION_EMPTY_QUERY_TEXT == throwable.message) {
                    setLoadingViewTextVisibility(false)
                    setRecViewItems()
                } else if (EXCEPTION_EMPTY_QUERY_RESULT == throwable.message) {
                    setLoadingViewTextVisibility(true)
                    setLoadingViewText(R.string.loading_view_text_no_currencies_found)
                }
            })
    }

    private fun processSearchQuery(text: String): Single<List<CurrencyPersist>> {

        return Single.create { emitter ->
            val formattedTxt = text.toLowerCase().trim()

            if (TextUtils.isEmpty(formattedTxt)) {
                emitter.onError(IllegalArgumentException(EXCEPTION_EMPTY_QUERY_TEXT))
            } else {
                val filteredList =
                    allCurrencies.filter { currency -> doesCurrencySatisfySearchQueryText(currency, formattedTxt) }

                if (filteredList.isEmpty()) {
                    emitter.onError(IllegalArgumentException(EXCEPTION_EMPTY_QUERY_RESULT))
                } else {
                    emitter.onSuccess(filteredList)
                }
            }
        }
    }

    private fun doesCurrencySatisfySearchQueryText(currency: CurrencyPersist, text: String): Boolean {
        val phrases = text.split("\\s+".toRegex())
        val found = BooleanArray(phrases.size)
        for (i in 0 until phrases.size) {
            if (currency.id.toLowerCase().indexOf(phrases[i]) > -1 || currency.name.toLowerCase().indexOf(phrases[i]) > -1) {
                found[i] = true
                continue
            }
        }

        return found.all { value -> value }
    }

    fun onItemClick(item: CurrencyPersist) {
        if (currencyTypeToReplace != CurrencyType.UNDEFINED
            && CurrencyUtils.canReplace(currencyPairDataService.pair, item.id, currencyTypeToReplace)
        ) {
            currencyPairDataService.pair =
                CurrencyUtils.replaceCurrency(currencyPairDataService.pair, item.id, currencyTypeToReplace)
            currencyPairDataService.isPairChanged = true
        }
        newDestinationEvent.value = Event(NavigationAction.POP_BACK_STACK.value)
    }
}