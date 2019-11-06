package com.teoryul.currencyconverter.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.teoryul.currencyconverter.persistence.model.CurrencyPersist
import io.reactivex.Single

@Dao
interface CurrencyDao : BaseDao<CurrencyPersist> {

    @Query("SELECT * FROM currency_table ORDER BY currency_id ASC")
    fun getAllCurrencies(): Single<List<CurrencyPersist>>

    @Query("SELECT * FROM currency_table WHERE currency_id = :id LIMIT 1")
    fun getCurrencyById(id: String): Single<CurrencyPersist>

    @Query("SELECT currency_name FROM currency_table WHERE currency_id = :id LIMIT 1")
    fun getCurrencyNameById(id: String): Single<String>

    @Query("SELECT currency_symbol FROM currency_table WHERE currency_id = :id LIMIT 1")
    fun getCurrencySymbolById(id: String): Single<String>
}