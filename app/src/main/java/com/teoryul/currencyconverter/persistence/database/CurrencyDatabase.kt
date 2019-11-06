package com.teoryul.currencyconverter.persistence.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.teoryul.currencyconverter.persistence.dao.ConversionRateDao
import com.teoryul.currencyconverter.persistence.dao.CurrencyDao
import com.teoryul.currencyconverter.persistence.dao.CurrencyPairDao
import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import com.teoryul.currencyconverter.persistence.model.CurrencyPairPersist
import com.teoryul.currencyconverter.persistence.model.CurrencyPersist

@Database(
    entities = [
        CurrencyPersist::class,
        CurrencyPairPersist::class,
        ConversionRatePersist::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun currencyPairDao(): CurrencyPairDao

    abstract fun conversionRateDao(): ConversionRateDao
}