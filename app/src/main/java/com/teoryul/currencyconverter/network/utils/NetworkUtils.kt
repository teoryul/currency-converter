package com.teoryul.currencyconverter.network.utils

import com.teoryul.currencyconverter.network.response.date.CurrencyRateForDatesResponse
import com.teoryul.currencyconverter.network.response.realtime.CurrencyRateListResponse
import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import com.teoryul.currencyconverter.utils.DateUtils

fun convertCurrencyRatesResponseToConversionRatePersist(
    currencyRateListResponse: CurrencyRateListResponse
): List<ConversionRatePersist> {
    val conversionRatesPersist = mutableListOf<ConversionRatePersist>()

    currencyRateListResponse.rates.forEach { currencyRateResponse ->
        conversionRatesPersist.add(
            ConversionRatePersist(
                currencyRateResponse.pair,
                currencyRateResponse.rate,
                DateUtils.getFormattedCurrentTime()
            )
        )
    }

    return conversionRatesPersist
}

fun convertCurrencyRateResponseToConversionRatePersist(rates: CurrencyRateForDatesResponse)
        : List<ConversionRatePersist> {
    val convertedRates = mutableListOf<ConversionRatePersist>()

    rates.dates.forEach { date ->
        convertedRates.add(
            ConversionRatePersist(
                rates.pair,
                date.rate,
                DateUtils.dateStringToLongTime(date.date)
            )
        )
    }

    return convertedRates
}