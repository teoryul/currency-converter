package com.teoryul.currencyconverter.ui.extensions

import kotlin.math.round

fun Float.round(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) {
        multiplier *= 10f
    }

    return round(this * multiplier) / multiplier
}