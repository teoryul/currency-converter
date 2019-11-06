package com.teoryul.currencyconverter.adapter.item

import com.teoryul.currencyconverter.utils.CurrencyUtils

data class CurrencyPairConfiguration(
    val fromCurrencyId: String,
    val toCurrencyId: String
) : Comparable<CurrencyPairConfiguration> {

    fun getAsPair(): String = CurrencyUtils.buildCurrencyPair(fromCurrencyId, toCurrencyId)

    override fun compareTo(other: CurrencyPairConfiguration): Int =
        other.getAsPair().compareTo(getAsPair())
}