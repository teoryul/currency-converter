package com.teoryul.currencyconverter.utils

import android.content.Context
import android.net.ConnectivityManager
import com.teoryul.currencyconverter.App

object InternetConnectivityHelper {

    private val connectivityManager = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isInternetConnectionAvailable(): Boolean =
        connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo.isAvailable
                && connectivityManager.activeNetworkInfo.isConnected
}