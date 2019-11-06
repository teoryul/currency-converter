package com.teoryul.currencyconverter.network.deserializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.teoryul.currencyconverter.network.response.currency.CurrencyListResponse
import com.teoryul.currencyconverter.network.response.currency.CurrencyResponse
import com.teoryul.currencyconverter.network.utils.ApiConstants
import java.lang.reflect.Type

class CurrencyDeserializer : JsonDeserializer<CurrencyListResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrencyListResponse {
        if (json == null) {
            return CurrencyListResponse(ArrayList())
        }

        val results: JsonElement = json.asJsonObject.get(ApiConstants.JSON_RESPONSE_CURRENCIES_RESULTS_KEY)
        val type = object : TypeToken<HashMap<String, CurrencyResponse>>() {}.type
        val currenciesMap: Map<String, CurrencyResponse> = Gson().fromJson(results, type)
        val currenciesList: List<CurrencyResponse> = ArrayList<CurrencyResponse>(currenciesMap.values)

        return CurrencyListResponse(currenciesList)
    }
}