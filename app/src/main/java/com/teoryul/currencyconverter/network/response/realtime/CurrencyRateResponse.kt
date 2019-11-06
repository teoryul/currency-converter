package com.teoryul.currencyconverter.network.response.realtime

data class CurrencyRateResponse(

    /**
     * I.e. EUR_BGN, EUR_USD
     */
    val pair: String,

    val rate: Float
)