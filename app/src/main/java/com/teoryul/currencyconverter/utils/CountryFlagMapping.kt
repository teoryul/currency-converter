package com.teoryul.currencyconverter.utils

/**
 * Class for handling mappings of currency code (USD, EUR) to its respective country flag (US, EU).
 * The currency api doesn't provide image resources for the flags.
 * The flags will be retrieved via Picasso from another api (https://www.countryflags.io/)
 * using only a url, ie. https://www.countryflags.io/eu/<shiny|flat>/64.png
 */
object CountryFlagMapping {

    /**
     * Missing in flag API - XAG, BTC, XOF, XDR, but handled using custom img resource.
     */
    private val countryFlagMap: HashMap<String, String> = hashMapOf(
        "FJD" to "FJ", "MXN" to "MX", "STD" to "ST", "SCR" to "SC",
        "LVL" to "LV", "GTQ" to "GT", "BBD" to "BB", "UGX" to "UG",
        "HNL" to "HN", "ZAR" to "ZA", "SLL" to "SL", "BSD" to "BS",
        "IQD" to "IQ", "GMD" to "GM", "CUP" to "CU", "TWD" to "TW",
        "RSD" to "RS", "DOP" to "DO", "KMF" to "KM", "MYR" to "MY",
        "FKP" to "FK", "GEL" to "GE", "BRL" to "BR", "UYU" to "UY",
        "MAD" to "MA", "CVE" to "CV", "TOP" to "TO", "AZN" to "AZ",
        "PGK" to "PG", "OMR" to "OM", "KES" to "KE", "SEK" to "SE",
        "UAH" to "UA", "BTN" to "BT", "GNF" to "GN", "MZN" to "MZ",
        "ERN" to "ER", "SVC" to "SV", "ARS" to "AR", "QAR" to "QA",
        "IRR" to "IR", "MRO" to "MR", "UZS" to "UZ", "CLF" to "CL",
        "THB" to "TH", "CNY" to "CN", "BDT" to "BD", "LYD" to "LY",
        "BMD" to "BM", "PHP" to "PH", "KWD" to "KW", "RUB" to "RU",
        "PYG" to "PY", "ISK" to "IS", "JMD" to "JM", "COP" to "CO",
        "USD" to "US", "DZD" to "DZ", "PAB" to "PA", "GGP" to "GG",
        "SGD" to "SG", "ETB" to "ET", "JEP" to "JE", "KGS" to "KG",
        "VUV" to "VU", "VEF" to "VE", "SOS" to "SO", "LAK" to "LA",
        "ZMK" to "ZM", "BND" to "BN", "TMT" to "TM", "LRD" to "LR",
        "HRK" to "HR", "CHF" to "CH", "DJF" to "DJ", "ALL" to "AL",
        "ZMW" to "ZM", "TZS" to "TZ", "VND" to "VN", "AUD" to "AU",
        "ILS" to "IL", "GHS" to "GH", "KPW" to "KP", "GYD" to "GY",
        "KHR" to "KH", "BOB" to "BO", "IDR" to "ID", "KYD" to "KY",
        "AMD" to "AM", "TRY" to "TR", "SHP" to "SH", "BWP" to "BW",
        "LBP" to "LB", "TJS" to "TJ", "JOD" to "JO", "HKD" to "HK",
        "AED" to "AE", "RWF" to "RW", "EUR" to "EU", "LSL" to "LS",
        "DKK" to "DK", "CAD" to "CA", "BGN" to "BG", "MMK" to "MM",
        "NOK" to "NO", "MUR" to "MU", "SYP" to "SY", "IMP" to "IM",
        "ZWL" to "ZW", "GIP" to "GI", "RON" to "RO", "LKR" to "LK",
        "NGN" to "NG", "CZK" to "CZ", "CRC" to "CR", "PKR" to "PK",
        "XCD" to "AG", "ANG" to "AN", "HTG" to "HT", "BHD" to "BH",
        "SZL" to "SZ", "SRD" to "SR", "KZT" to "KZ", "TTD" to "TT",
        "SAR" to "SA", "YER" to "YE", "MVR" to "MV", "AFN" to "AF",
        "INR" to "IN", "NPR" to "NP", "AWG" to "AW", "KRW" to "KR",
        "JPY" to "JP", "MNT" to "MN", "PLN" to "PL", "AOA" to "AO",
        "GBP" to "GB", "SBD" to "SB", "HUF" to "HU", "BYR" to "BY",
        "BIF" to "BI", "MWK" to "MW", "MGA" to "MG", "BZD" to "BZ",
        "BAM" to "BA", "MOP" to "MO", "EGP" to "EG", "NAD" to "NA",
        "NIO" to "NI", "PEN" to "PE", "WST" to "WS", "NZD" to "NZ",
        "XAG" to "", "XPF" to "PF", "BTC" to "BTC", "CDF" to "CD",
        "CLP" to "CL", "TND" to "TN", "CUC" to "CU", "SDG" to "SD",
        "XOF" to "", "MKD" to "MK", "XAF" to "CF", "MDL" to "MD",
        "BYN" to "BY", "XDR" to ""
    )

    fun getFlagId(currencyId: String?): String? = countryFlagMap[currencyId]
}