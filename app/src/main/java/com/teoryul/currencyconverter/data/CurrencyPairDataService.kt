package com.teoryul.currencyconverter.data

import com.teoryul.currencyconverter.utils.DEFAULT_CONVERSION_RATE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyPairDataService @Inject constructor() {

    var pair: String = ""
    var rate: Float = DEFAULT_CONVERSION_RATE
    var isPairChanged: Boolean = false
}