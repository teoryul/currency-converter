package com.teoryul.currencyconverter.utils

import android.text.TextUtils
import com.teoryul.currencyconverter.App
import com.teoryul.currencyconverter.data.CurrencyType
import java.util.*

/**
 * Class for handling currency pair string manipulations, retrieving system locale currency code.
 */
object CurrencyUtils {

    fun getLocaleCurrencyCode(): String =
        Currency.getInstance(App.instance.resources.configuration.locale).currencyCode

    fun buildCurrencyPair(currencyCode1: String, currencyCode2: String? = ""): String =
        if (TextUtils.isEmpty(currencyCode2)) {
            currencyCode1
                .plus(CURRENCY_PAIR_DELIMITER)
                .plus(currencyCode1)
        } else {
            currencyCode1
                .plus(CURRENCY_PAIR_DELIMITER)
                .plus(currencyCode2)
        }

    fun swapCurrencyPairIds(pair: String): String =
        pair.takeLast(CURRENCY_CODE_LENGTH).plus(CURRENCY_PAIR_DELIMITER).plus(pair.take(CURRENCY_CODE_LENGTH))

    fun buildCurrencyPairsQueryParam(vararg pairs: String): String =
        pairs.joinToString(CURRENCY_PAIR_QUERY_PARAM_SEPARATOR)

    fun areCurrencyCodesEqual(pair: String): Boolean =
        pair.take(CURRENCY_CODE_LENGTH) == pair.takeLast(CURRENCY_CODE_LENGTH)

    fun canReplace(pair: String, newCurrencyId: String, which: CurrencyType): Boolean =
        if (which == CurrencyType.FROM_CURRENCY) {
            pair.take(CURRENCY_CODE_LENGTH) != newCurrencyId
        } else {
            pair.takeLast(CURRENCY_CODE_LENGTH) != newCurrencyId
        }

    fun replaceCurrency(pair: String, newCurrencyId: String, which: CurrencyType): String =
        if (which == CurrencyType.FROM_CURRENCY) {
            pair.replaceBefore(CURRENCY_PAIR_DELIMITER, newCurrencyId)
        } else {
            pair.replaceAfter(CURRENCY_PAIR_DELIMITER, newCurrencyId)
        }
}