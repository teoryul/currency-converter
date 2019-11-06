package com.teoryul.currencyconverter.network.service

import com.teoryul.currencyconverter.network.response.currency.CurrencyListResponse
import com.teoryul.currencyconverter.network.response.date.CurrencyRateForDatesListResponse
import com.teoryul.currencyconverter.network.response.realtime.CurrencyRateListResponse
import com.teoryul.currencyconverter.network.retrofit.CurrencyApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyApiService
@Inject constructor(private val currencyApi: CurrencyApi) {

    fun getAllCurrencies(): Single<CurrencyListResponse> {
        return currencyApi.getAllCurrencies()
    }

    fun getRealTimeConversionRate(currencyPairs: String): Single<CurrencyRateListResponse> {
        return currencyApi.getRealTimeConversionRate(currencyPairs)
    }

    fun getPastConversionRateForDate(
        currencyPairs: String,
        date: String
    ): Single<CurrencyRateForDatesListResponse> {
        return currencyApi.getPastConversionRateForDate(currencyPairs, date)
    }

    fun getPastConversionRatesForDateRange(
        currencyPairs: String,
        date: String,
        endDate: String
    ): Single<CurrencyRateForDatesListResponse> {
        return currencyApi.getPastConversionRatesForDateRange(currencyPairs, date, endDate)
    }
}