package com.teoryul.currencyconverter.utils

import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.teoryul.currencyconverter.BuildConfig
import com.teoryul.currencyconverter.R

private const val URL_SUFFIX = "/flat/64.png"

/**
 * Loads flag images from the flags API (https://www.countryflags.io/)
 * or in a few cases, where the api is missing the required flag, from drawables.
 * API url example: https://www.countryflags.io/eu/flat/64.png
 */
object PicassoImageLoader {

    fun setImage(imgView: ImageView, currencyId: String?) {
        if (TextUtils.isEmpty(currencyId)) {

            return
        }

        val flagId = CountryFlagMapping.getFlagId(currencyId)

        if (TextUtils.isEmpty(flagId)) {
            Picasso.get()
                .load(R.drawable.flag_fallback)
                .into(imgView)

            return
        }

        // Handles custom case for Bitcoin
        if ("BTC" == flagId) {
            Picasso.get()
                .load(R.drawable.btc)
                .into(imgView)

            return
        }

        val url = buildUrl(flagId)

        Picasso.get()
            .load(url)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(imgView, object : Callback {

                override fun onSuccess() {
                    // Not used
                }

                override fun onError(e: Exception?) = Picasso.get().load(url).into(imgView)
            })
    }

    private fun buildUrl(flagId: String?): String = BuildConfig.FLAGS_URL.plus(flagId).plus(URL_SUFFIX)
}