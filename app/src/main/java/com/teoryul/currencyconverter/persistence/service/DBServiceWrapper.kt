package com.teoryul.currencyconverter.persistence.service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DBServiceWrapper
@Inject constructor(
    val conversionRateDBService: ConversionRateDBService,
    val currencyDBService: CurrencyDBService,
    val currencyPairDBService: CurrencyPairDBService
)