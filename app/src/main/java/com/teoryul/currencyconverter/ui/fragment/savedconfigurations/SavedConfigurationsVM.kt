package com.teoryul.currencyconverter.ui.fragment.savedconfigurations

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableArrayList
import android.util.Log
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.adapter.item.CurrencyPairConfiguration
import com.teoryul.currencyconverter.event.Event
import com.teoryul.currencyconverter.event.NavigationAction
import com.teoryul.currencyconverter.persistence.model.CurrencyPairPersist
import com.teoryul.currencyconverter.ui.fragment.BaseVM
import com.teoryul.currencyconverter.utils.*
import io.reactivex.functions.Consumer
import java.util.*
import javax.inject.Inject

class SavedConfigurationsVM @Inject constructor() : BaseVM() {

    var recViewItems = ObservableArrayList<CurrencyPairConfiguration>()
    val recViewItemDeleteEvent = MutableLiveData<Event<String>>()
    private lateinit var deletedRecViewItem: CurrencyPairConfiguration
    private var deletedRecViewItemPosition: Int = -1

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        loadCurrencyPairData()
    }

    private fun loadCurrencyPairData() {
        setLoadingViewProgressBarVisibility(true)
        subscribeSingle(
            dbService.currencyPairDBService.getAllCurrencyPairsSavedByUser(),
            Consumer { currencyPairs ->
                if (currencyPairs.isEmpty()) {
                    setLoadingViewText(R.string.loading_view_text_no_saved_pair_configurations)
                    setLoadingViewTextVisibility(true)
                } else {
                    processCurrencyPairsDBQuery(currencyPairs)
                }
                setLoadingViewProgressBarVisibility(false)
            },
            Consumer { throwable ->
                Log.e(LOG_ERROR_TAG_FAILED_QUERY, throwable.message)
                setErrorEvent(Event(R.string.message_dialog_db_query_error))
            }
        )
    }

    private fun processCurrencyPairsDBQuery(currencyPairs: List<CurrencyPairPersist>) {
        if (!recViewItems.isEmpty()) {
            recViewItems.clear()
        }
        val tmpList = mutableListOf<CurrencyPairConfiguration>()
        currencyPairs.forEach { pair ->
            tmpList.add(
                CurrencyPairConfiguration(
                    pair.pair.take(CURRENCY_CODE_LENGTH),
                    pair.pair.takeLast(CURRENCY_CODE_LENGTH)
                )
            )
        }
        if (!recViewItems.isEmpty()) {
            recViewItems.clear()
        }
        recViewItems.addAll(tmpList)
    }

    fun onItemClick(item: CurrencyPairConfiguration) {
        currencyPairDataService.pair = item.getAsPair()
        currencyPairDataService.isPairChanged = true
        newDestinationEvent.value = Event(NavigationAction.POP_BACK_STACK.value)
    }

    fun onFabAddClick() {
        if (!CurrencyUtils.areCurrencyCodesEqual(currencyPairDataService.pair)) {
            val newPairConfig = CurrencyPairConfiguration(
                currencyPairDataService.pair.take(CURRENCY_CODE_LENGTH),
                currencyPairDataService.pair.takeLast(CURRENCY_CODE_LENGTH)
            )
            persistPairConfig(newPairConfig, true)
            addPairConfig(newPairConfig)
        }
    }

    fun onSwipeDelete(itemPosition: Int) {
        deletedRecViewItemPosition = itemPosition
        deletedRecViewItem = recViewItems.removeAt(itemPosition)
        persistPairConfig(deletedRecViewItem, false)

        if (recViewItems.isEmpty()) {
            setLoadingViewText(R.string.loading_view_text_no_saved_pair_configurations)
            setLoadingViewTextVisibility(true)
        }

        recViewItemDeleteEvent.value = Event(
            deletedRecViewItem.getAsPair().replace(
                CURRENCY_PAIR_UNDERSCORE_DELIMITER,
                CURRENCY_PAIR_DASH_DELIMITER
            )
        )
    }

    fun onUndoSwipeDelete() {
        addPairConfig(deletedRecViewItem, deletedRecViewItemPosition)
        persistPairConfig(deletedRecViewItem, true)
    }

    private fun addPairConfig(newPairConfig: CurrencyPairConfiguration, index: Int? = null) {
        if (!recViewItems.isEmpty()) {
            for (pairConfig in recViewItems) {
                if (pairConfig.getAsPair() == currencyPairDataService.pair) {

                    return
                }
            }
            if (index != null) {
                recViewItems.add(index, newPairConfig)
            } else {
                val linkedList = LinkedList(recViewItems)
                linkedList.push(newPairConfig)
                recViewItems.clear()
                recViewItems.addAll(linkedList)
            }
        } else {
            recViewItems.add(newPairConfig)
            setLoadingViewTextVisibility(false)
        }
    }

    private fun persistPairConfig(newPairConfig: CurrencyPairConfiguration, savedByUser: Boolean) {
        val pair = CurrencyPairPersist(newPairConfig.getAsPair(), savedByUser)
        dbService.currencyPairDBService.insertItemWithReplaceStrategy(pair)
    }
}