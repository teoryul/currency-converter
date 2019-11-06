package com.teoryul.currencyconverter.persistence.repository

import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import io.reactivex.Single

interface ConversionRateRepository : BaseRepository<ConversionRatePersist> {

    fun getAllConversionRatesForCurrencyPair(pair: String): Single<List<ConversionRatePersist>>

    fun getAllConversionRatesForCurrencyPairByDate(pair: String, date: Long): Single<List<ConversionRatePersist>>

    fun getAllConversionRatesForCurrencyPairByDateRange(pair: String, startDate: Long, endDate: Long):
            Single<List<ConversionRatePersist>>
}