package com.teoryul.currencyconverter.network.deserializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.teoryul.currencyconverter.network.response.date.CurrencyRateForDatesListResponse
import com.teoryul.currencyconverter.network.response.date.CurrencyRateForDatesResponse
import com.teoryul.currencyconverter.network.response.date.ExchangeRateForDateResponse
import java.lang.reflect.Type

class CurrencyRateForDatesDeserializer : JsonDeserializer<CurrencyRateForDatesListResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrencyRateForDatesListResponse {
        if (json == null) {
            return CurrencyRateForDatesListResponse(ArrayList())
        }

        val type = object : TypeToken<HashMap<String, HashMap<String, Float>>>() {}.type
        val pairsMap: Map<String, Map<String, Float>> = Gson().fromJson(json, type)
        val pairsList = ArrayList<CurrencyRateForDatesResponse>()
        pairsMap.forEach { (pair, dates) ->
            val datesList = ArrayList<ExchangeRateForDateResponse>()
            dates.forEach { (date, rate) ->
                datesList.add(ExchangeRateForDateResponse(date, rate))
            }
            pairsList.add(CurrencyRateForDatesResponse(pair, datesList))
        }

        return CurrencyRateForDatesListResponse(pairsList)
    }
}