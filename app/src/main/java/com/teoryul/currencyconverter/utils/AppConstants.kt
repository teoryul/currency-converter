package com.teoryul.currencyconverter.utils

import java.text.DecimalFormat

const val SIMPLE_DATE_FORMAT_PATTERN_DATE: String = "yyyy-MM-dd"
const val SIMPLE_DATE_FORMAT_PATTERN_YEAR: String = "yyyy"
const val SIMPLE_DATE_FORMAT_PATTERN_MONTH: String = "MMM"
const val SIMPLE_DATE_FORMAT_PATTERN_DAY: String = "d"
const val SIMPLE_DATE_FORMAT_PATTERN_DAY_AND_MONTH: String = "d MMM"
const val ONE_DAY_AS_MILLIS = 86400000L
const val HISTORICAL_DATA_DATE_RANGE = 8

const val LOG_ERROR_TAG_FAILED_QUERY = "Failed db query"
const val LOG_ERROR_TAG_FAILED_REQUEST = "Failed api request"
const val LOG_ERROR_MESSAGE_EMPTY_RESPONSE = "Empty response object"

const val SHARED_PREFERENCES_KEY_DID_FETCH_ALL_CURRENCIES_FROM_API: String = "allCurrFetch"
const val SHARED_PREFERENCES_KEY_LAST_USED_CURRENCY_PAIR: String = "lastUsedPair"
const val SHARED_PREFERENCES_KEY_SAVED_INPUT_AMOUNT: String = "inputAmount"

const val BUNDLE_KEY_CURRENCY_TYPE = "currencyType"

val DECIMAL_FORMAT = DecimalFormat("#.####")
const val DEFAULT_INPUT_AMOUNT = 1F
const val DEFAULT_CONVERSION_RATE = 1F
const val CURRENCY_CODE_LENGTH = 3
const val CURRENCY_PAIR_UNDERSCORE_DELIMITER = "_"
const val CURRENCY_PAIR_DASH_DELIMITER = " - "
const val CURRENCY_PAIR_QUERY_PARAM_SEPARATOR = ","
const val CURRENCY_INPUT_AMOUNT_ZERO = "0"
const val CURRENCY_DECIMAL_POINT = "."

const val EXCEPTION_EMPTY_QUERY_TEXT = "Empty search query input text"
const val EXCEPTION_EMPTY_QUERY_RESULT = "Empty search query list result"