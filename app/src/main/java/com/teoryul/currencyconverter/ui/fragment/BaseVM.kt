package com.teoryul.currencyconverter.ui.fragment

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.data.CurrencyPairDataService
import com.teoryul.currencyconverter.event.Event
import com.teoryul.currencyconverter.network.service.CurrencyApiService
import com.teoryul.currencyconverter.persistence.service.DBServiceWrapper
import com.teoryul.currencyconverter.utils.RxUtils
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

abstract class BaseVM : ViewModel(), LifecycleObserver {

    @Inject
    protected lateinit var currencyApiService: CurrencyApiService
    @Inject
    protected lateinit var dbService: DBServiceWrapper
    @Inject
    protected lateinit var currencyPairDataService: CurrencyPairDataService

    private var compositeDisposable: CompositeDisposable? = null

    val newDestinationEvent = MutableLiveData<Event<Int>>()
    val errorEvent = MutableLiveData<Event<Int>>()
    val loadingViewProgressBarVisibility = MutableLiveData<Boolean>().apply { value = false }
    val loadingViewText = MutableLiveData<Int>().apply { value = R.string.empty }
    val loadingViewTextVisibility = MutableLiveData<Boolean>().apply { value = false }

    fun setNewDestination(actionId: Int) = newDestinationEvent.apply { value = Event(actionId) }

    protected fun setErrorEvent(event: Event<Int>) = errorEvent.postValue(event)

    protected fun setLoadingViewProgressBarVisibility(isVisible: Boolean) =
        loadingViewProgressBarVisibility.postValue(isVisible)

    protected fun setLoadingViewText(resId: Int) = loadingViewText.postValue(resId)

    protected fun setLoadingViewTextVisibility(isVisible: Boolean) = loadingViewTextVisibility.postValue(isVisible)

    protected fun <T> subscribeSingle(observable: Single<T>, onSuccess: Consumer<T>, onError: Consumer<Throwable>) =
        getCompositeDisposable().add(observable.compose(RxUtils.applySingleSchedulers()).subscribe(onSuccess, onError))

    private fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposable == null || compositeDisposable!!.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }

        return compositeDisposable as CompositeDisposable
    }

    override fun onCleared() {
        getCompositeDisposable().dispose()
        super.onCleared()
    }
}