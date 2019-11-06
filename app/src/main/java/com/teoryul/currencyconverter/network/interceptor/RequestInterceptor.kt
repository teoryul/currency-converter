package com.teoryul.currencyconverter.network.interceptor

import com.teoryul.currencyconverter.network.utils.ApiConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

object RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val newRequest: Request = originalRequest.newBuilder()
            .addHeader(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_ACCEPT_JSON)
            .addHeader(ApiConstants.HEADER_KEY_ACCEPT_CHARSET, ApiConstants.HEADER_VALUE_ACCEPT_CHARSET_UTF8)
            .method(originalRequest.method(), null)
            .build()

        return chain.proceed(newRequest)
    }
}