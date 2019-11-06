package com.teoryul.currencyconverter

import android.preference.PreferenceManager
import com.squareup.picasso.LruCache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.teoryul.currencyconverter.dagger.component.DaggerAppComponent
import com.teoryul.currencyconverter.dagger.module.AppModule
import com.teoryul.currencyconverter.dagger.module.NetworkModule
import com.teoryul.currencyconverter.dagger.module.RoomModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        setUpSettingsDefaultValues()
        setUpPicassoInstance()
    }

    private fun setUpSettingsDefaultValues() =
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)

    private fun setUpPicassoInstance() =
        Picasso.setSingletonInstance(
            Picasso.Builder(this)
                .downloader(OkHttp3Downloader(this, Long.MAX_VALUE))
                .memoryCache(LruCache(this))
                .build()
        )

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .roomModule(RoomModule(this))
            .build()
}