package com.teoryul.currencyconverter.network.response.date

data class CurrencyRateForDatesResponse(

    /**
     * I.e. EUR_BGN, EUR_USD
     */
    val pair: String,

    val dates: List<ExchangeRateForDateResponse>
)