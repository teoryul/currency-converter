package com.teoryul.currencyconverter.network.deserializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.teoryul.currencyconverter.network.response.realtime.CurrencyRateListResponse
import com.teoryul.currencyconverter.network.response.realtime.CurrencyRateResponse
import java.lang.reflect.Type

class CurrencyRateDeserializer : JsonDeserializer<CurrencyRateListResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrencyRateListResponse {
        if (json == null) {
            return CurrencyRateListResponse(ArrayList())
        }

        val type = object : TypeToken<HashMap<String, Float>>() {}.type
        val ratesMap: Map<String, Float> = Gson().fromJson(json, type)
        val ratesList = ArrayList<CurrencyRateResponse>()
        ratesMap.forEach { (pair, rate) ->
            ratesList.add(CurrencyRateResponse(pair, rate))
        }

        return CurrencyRateListResponse(ratesList)
    }
}