package com.teoryul.currencyconverter.dagger.module

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.teoryul.currencyconverter.data.CurrencyPairDataService
import com.teoryul.currencyconverter.utils.AppExecutor
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application {

        return application
    }

    @Provides
    fun provideContext(): Context {

        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideCurrencyPairDataService(): CurrencyPairDataService {

        return CurrencyPairDataService()
    }

    @Singleton
    @Provides
    fun provideAppExecutor(): AppExecutor {

        return AppExecutor(Executors.newSingleThreadExecutor())
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferences {

        return application.getSharedPreferences(application.packageName, Activity.MODE_PRIVATE)
    }
}
