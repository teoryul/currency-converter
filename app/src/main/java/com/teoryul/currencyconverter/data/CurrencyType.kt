package com.teoryul.currencyconverter.data

enum class CurrencyType(val value: Int) {
    UNDEFINED(0),
    FROM_CURRENCY(1),
    TO_CURRENCY(2);

    companion object {

        private val types = values().associate { it.value to it }

        fun findByValue(value: Int) = types[value]
    }
}