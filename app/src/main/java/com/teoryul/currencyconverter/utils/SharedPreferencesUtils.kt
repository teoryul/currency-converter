package com.teoryul.currencyconverter.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import com.teoryul.currencyconverter.App
import com.teoryul.currencyconverter.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesUtils
@Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun read(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    fun write(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun read(key: String, defValue: String): String? {
        return sharedPreferences.getString(key, defValue)
    }

    fun write(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun read(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    fun write(key: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun read(key: String, defValue: Long): Long {
        return sharedPreferences.getLong(key, defValue)
    }

    fun write(key: String, value: Long) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun read(key: String, defValue: Float): Float {
        return sharedPreferences.getFloat(key, defValue)
    }

    fun write(key: String, value: Float) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * Loads the last used currency pair, eg. USD_BGN, from shared preferences,
     * or a default one either from system locale, preference manager or strings.xml.
     */
    fun loadCurrencyPair(): String? {
        val pair = read(SHARED_PREFERENCES_KEY_LAST_USED_CURRENCY_PAIR, "")

        if (!TextUtils.isEmpty(pair)) {

            return pair
        }

        val defaultCurrencyCode = App.instance.getString(R.string.default_currency_code)
        var fromCurrencyCode: String = CurrencyUtils.getLocaleCurrencyCode()
        var toCurrencyCode: String? = PreferenceManager.getDefaultSharedPreferences(App.instance)
            .getString(App.instance.getString(R.string.list_preference_default_currency_key), "")

        if (TextUtils.isEmpty(fromCurrencyCode)) {
            fromCurrencyCode = defaultCurrencyCode
        }

        if (TextUtils.isEmpty(toCurrencyCode)) {
            toCurrencyCode = defaultCurrencyCode
        }

        return CurrencyUtils.buildCurrencyPair(fromCurrencyCode, toCurrencyCode)
    }

    fun saveCurrencyPair(pair: String) = write(SHARED_PREFERENCES_KEY_LAST_USED_CURRENCY_PAIR, pair)

    /**
     * Returns if the user has checked that settings from the settings screen.
     */
    private fun shouldSaveInputAmount(): Boolean =
        PreferenceManager.getDefaultSharedPreferences(App.instance).getBoolean(
            App.instance.getString(R.string.switch_preference_save_input_amount_key), false
        )

    /**
     * Loads the stored input amount from shared preferences.
     */
    fun loadInputAmount(): Float {

        return if (shouldSaveInputAmount()) {
            read(SHARED_PREFERENCES_KEY_SAVED_INPUT_AMOUNT, DEFAULT_INPUT_AMOUNT)
        } else {
            DEFAULT_INPUT_AMOUNT
        }
    }

    /**
     * Saves the last input amount if the user has checked that settings from the settings screen.
     * TODO: 24.06.2019 will be fully implemented in a future branch dedicated to the settings screen
     */
    fun saveInputAmountPreference(inputAmount: String?) =
        if (shouldSaveInputAmount()) {
            inputAmount?.toFloat()?.let { amount ->
                write(SHARED_PREFERENCES_KEY_SAVED_INPUT_AMOUNT, amount)
            }
        } else {
            write(SHARED_PREFERENCES_KEY_SAVED_INPUT_AMOUNT, DEFAULT_INPUT_AMOUNT)
        }

    /**
     * Returns whether the currency list has been dowloaded from the API and stored in the DB.
     */
    fun didFetchAllCurrenciesFromApi(): Boolean = read(SHARED_PREFERENCES_KEY_DID_FETCH_ALL_CURRENCIES_FROM_API, false)
}