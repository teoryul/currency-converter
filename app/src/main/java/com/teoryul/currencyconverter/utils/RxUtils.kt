package com.teoryul.currencyconverter.utils

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtils {

    fun <T> applySingleSchedulers(): SingleTransformer<T, T> {

        return SingleTransformer { upstream ->
            upstream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }
    }
}