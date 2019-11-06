package com.teoryul.currencyconverter.binding

import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter("visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.GONE else View.VISIBLE
}