package com.teoryul.currencyconverter.network.retrofit

import com.teoryul.currencyconverter.BuildConfig
import com.teoryul.currencyconverter.network.response.currency.CurrencyListResponse
import com.teoryul.currencyconverter.network.response.date.CurrencyRateForDatesListResponse
import com.teoryul.currencyconverter.network.response.realtime.CurrencyRateListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("currencies")
    fun getAllCurrencies(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Single<CurrencyListResponse>

    @GET("convert")
    fun getRealTimeConversionRate(
        @Query("q") currencyPairs: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("compact") compactType: String = "ultra"
    ): Single<CurrencyRateListResponse>

    @GET("convert")
    fun getPastConversionRateForDate(
        @Query("q") currencyPairs: String,
        @Query("date") date: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("compact") compactType: String = "ultra"
    ): Single<CurrencyRateForDatesListResponse>

    @GET("convert")
    fun getPastConversionRatesForDateRange(
        @Query("q") currencyPairs: String,
        @Query("date") date: String,
        @Query("endDate") endDate: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("compact") compactType: String = "ultra"
    ): Single<CurrencyRateForDatesListResponse>
}