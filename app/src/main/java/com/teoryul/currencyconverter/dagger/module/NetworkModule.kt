package com.teoryul.currencyconverter.dagger.module

import android.app.Application
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import com.teoryul.currencyconverter.BuildConfig
import com.teoryul.currencyconverter.network.deserializer.CurrencyDeserializer
import com.teoryul.currencyconverter.network.deserializer.CurrencyRateDeserializer
import com.teoryul.currencyconverter.network.deserializer.CurrencyRateForDatesDeserializer
import com.teoryul.currencyconverter.network.interceptor.RequestInterceptor
import com.teoryul.currencyconverter.network.response.currency.CurrencyListResponse
import com.teoryul.currencyconverter.network.response.date.CurrencyRateForDatesListResponse
import com.teoryul.currencyconverter.network.response.realtime.CurrencyRateListResponse
import com.teoryul.currencyconverter.network.retrofit.CurrencyApi
import com.teoryul.currencyconverter.network.utils.ApiConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    @Singleton
    @Provides
    fun provideChuckInterceptor(application: Application): ChuckInterceptor {
        return ChuckInterceptor(application)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckInterceptor: ChuckInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckInterceptor)
            .addInterceptor(RequestInterceptor)
            .connectTimeout(ApiConstants.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(ApiConstants.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(ApiConstants.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()

        gsonBuilder.registerTypeAdapter(
            CurrencyListResponse::class.java,
            CurrencyDeserializer()
        )

        gsonBuilder.registerTypeAdapter(
            CurrencyRateListResponse::class.java,
            CurrencyRateDeserializer()
        )

        gsonBuilder.registerTypeAdapter(
            CurrencyRateForDatesListResponse::class.java,
            CurrencyRateForDatesDeserializer()
        )

        val gson = gsonBuilder.create()

        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }
}