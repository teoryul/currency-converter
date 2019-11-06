package com.teoryul.currencyconverter.network.response.currency

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(

    /**
     * I.e. BGN, EUR, USD
     */
    @SerializedName("id")
    val id: String,

    /**
     * I.e. Euro, Dollar
     */
    @SerializedName("currencyName")
    val name: String,

    /**
     * I.e. €, $
     */
    @SerializedName("currencySymbol")
    val symbol: String?
)