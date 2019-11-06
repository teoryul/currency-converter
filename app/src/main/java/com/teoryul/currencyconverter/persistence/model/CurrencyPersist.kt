package com.teoryul.currencyconverter.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "currency_table")
data class CurrencyPersist(

    /**
     * I.e. BGN, EUR, USD
     */
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "currency_id")
    val id: String,

    /**
     * I.e. Euro, Dollar
     */
    @ColumnInfo(name = "currency_name")
    val name: String,

    /**
     * I.e. €, $
     */
    @ColumnInfo(name = "currency_symbol")
    val symbol: String?
) : Comparable<CurrencyPersist> {

    override fun compareTo(other: CurrencyPersist): Int = id.compareTo(other.id)
}