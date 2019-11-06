package com.teoryul.currencyconverter.ui.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog

fun Fragment.showDialog(builderFunction: AlertDialog.Builder.() -> Any) {
    val builder = activity?.let { AlertDialog.Builder(it) }
    builder?.builderFunction()
    builder?.create()?.show()
}