package com.teoryul.currencyconverter.persistence.repository

import com.teoryul.currencyconverter.persistence.model.CurrencyPairPersist
import io.reactivex.Single

interface CurrencyPairRepository : BaseRepository<CurrencyPairPersist> {

    fun insertCurrencyPairWithAbortStrategy(currencyPairPersist: CurrencyPairPersist): Single<Long>

    fun getAllCurrencyPairs(): Single<List<CurrencyPairPersist>>

    fun getAllCurrencyPairsSavedByUser(): Single<List<CurrencyPairPersist>>
}