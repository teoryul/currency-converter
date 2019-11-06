package com.teoryul.currencyconverter.network.response.date

data class ExchangeRateForDateResponse(

    /**
     * I.e. 2019-05-29
     */
    val date: String,

    val rate: Float
)