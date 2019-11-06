package com.teoryul.currencyconverter.dagger.module

import android.app.Application
import android.arch.persistence.room.Room
import com.teoryul.currencyconverter.persistence.dao.ConversionRateDao
import com.teoryul.currencyconverter.persistence.dao.CurrencyDao
import com.teoryul.currencyconverter.persistence.dao.CurrencyPairDao
import com.teoryul.currencyconverter.persistence.database.CurrencyDatabase
import dagger.Module
import dagger.Provides

@Module
class RoomModule(application: Application) {

    private val currencyDatabase: CurrencyDatabase = Room
        .databaseBuilder(application, CurrencyDatabase::class.java, "curr_conv_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCurrencyDatabase(): CurrencyDatabase {
        return currencyDatabase
    }

    @Provides
    fun provideCurrencyDao(): CurrencyDao {
        return currencyDatabase.currencyDao()
    }

    @Provides
    fun provideCurrencyPairDao(): CurrencyPairDao {
        return currencyDatabase.currencyPairDao()
    }

    @Provides
    fun provideConversionRateDao(): ConversionRateDao {
        return currencyDatabase.conversionRateDao()
    }
}