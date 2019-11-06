package com.teoryul.currencyconverter.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "currency_pair_table")
data class CurrencyPairPersist(

    /**
     * I.e. EUR_BGN, EUR_USD
     */
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pair")
    val pair: String,

    @ColumnInfo(name = "saved_by_user")
    val savedByUser: Boolean = false
)