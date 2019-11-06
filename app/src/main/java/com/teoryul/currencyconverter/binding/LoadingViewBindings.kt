package com.teoryul.currencyconverter.binding

import android.databinding.BindingAdapter
import com.teoryul.currencyconverter.view.loading.LoadingView

@BindingAdapter("loadingViewProgressBarVisibility")
fun setProgressBarVisibility(loadingView: LoadingView, isVisible: Boolean) =
    loadingView.setProgressBarVisibility(isVisible)

@BindingAdapter("loadingViewText")
fun setLoadingText(loadingView: LoadingView, resId: Int) =
    loadingView.setLoadingText(resId)

@BindingAdapter("loadingViewTextVisibility")
fun setLoadingTextVisibility(loadingView: LoadingView, isVisible: Boolean) =
    loadingView.setLoadingTextVisibility(isVisible)