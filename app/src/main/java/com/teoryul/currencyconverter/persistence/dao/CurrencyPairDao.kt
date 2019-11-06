package com.teoryul.currencyconverter.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.teoryul.currencyconverter.persistence.model.CurrencyPairPersist
import io.reactivex.Single

@Dao
interface CurrencyPairDao : BaseDao<CurrencyPairPersist> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCurrencyPairWithAbortStrategy(currencyPairPersist: CurrencyPairPersist): Long

    @Query("SELECT * FROM currency_pair_table")
    fun getAllCurrencyPairs(): Single<List<CurrencyPairPersist>>

    @Query("SELECT * FROM currency_pair_table WHERE saved_by_user = 1 ORDER BY pair ASC")
    fun getAllCurrencyPairsSavedByUser(): Single<List<CurrencyPairPersist>>
}