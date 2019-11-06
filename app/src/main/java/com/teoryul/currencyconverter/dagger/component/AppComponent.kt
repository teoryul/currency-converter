package com.teoryul.currencyconverter.dagger.component

import com.teoryul.currencyconverter.dagger.module.AppModule
import com.teoryul.currencyconverter.dagger.module.NetworkModule
import com.teoryul.currencyconverter.dagger.module.RoomModule
import com.teoryul.currencyconverter.dagger.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RoomModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication>