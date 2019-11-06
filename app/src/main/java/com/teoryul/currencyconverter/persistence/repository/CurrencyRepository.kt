package com.teoryul.currencyconverter.persistence.repository

import com.teoryul.currencyconverter.persistence.model.CurrencyPersist
import io.reactivex.Single

interface CurrencyRepository : BaseRepository<CurrencyPersist> {

    fun getAllCurrencies(): Single<List<CurrencyPersist>>

    fun getCurrencyById(id: String): Single<CurrencyPersist>

    fun getCurrencyNameById(id: String): Single<String>

    fun getCurrencySymbolById(id: String): Single<String>
}