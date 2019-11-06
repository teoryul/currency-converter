package com.teoryul.currencyconverter.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import io.reactivex.Single

@Dao
interface ConversionRateDao : BaseDao<ConversionRatePersist> {

    @Query("SELECT * FROM conversion_rate_table WHERE currency_pair = :pair")
    fun getAllConversionRatesForCurrencyPair(pair: String): Single<List<ConversionRatePersist>>

    @Query("SELECT * FROM conversion_rate_table WHERE currency_pair = :pair AND date = :date")
    fun getAllConversionRatesForCurrencyPairByDate(pair: String, date: Long): Single<List<ConversionRatePersist>>

    @Query("SELECT * FROM conversion_rate_table WHERE currency_pair = :pair AND date >= :startDate AND date <= :endDate ORDER BY date ASC")
    fun getAllConversionRatesForCurrencyPairByDateRange(pair: String, startDate: Long, endDate: Long):
            Single<List<ConversionRatePersist>>
}