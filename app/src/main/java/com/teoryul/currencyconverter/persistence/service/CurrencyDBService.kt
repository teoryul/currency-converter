package com.teoryul.currencyconverter.persistence.service

import com.teoryul.currencyconverter.persistence.dao.CurrencyDao
import com.teoryul.currencyconverter.persistence.model.CurrencyPersist
import com.teoryul.currencyconverter.persistence.repository.CurrencyRepository
import com.teoryul.currencyconverter.utils.AppExecutor
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyDBService
@Inject constructor(
    private val dao: CurrencyDao,
    private val executor: AppExecutor
) : CurrencyRepository {

    override fun getAllCurrencies(): Single<List<CurrencyPersist>> {
        return dao.getAllCurrencies()
    }

    override fun getCurrencyById(id: String): Single<CurrencyPersist> {
        return dao.getCurrencyById(id)
    }

    override fun getCurrencyNameById(id: String): Single<String> {
        return dao.getCurrencyNameById(id)
    }

    override fun getCurrencySymbolById(id: String): Single<String> {
        return dao.getCurrencySymbolById(id)
    }

    override fun insertItemWithIgnoreStrategy(t: CurrencyPersist) {
        executor.execute(
            runnable = Runnable {
                dao.insertItemWithIgnoreStrategy(t)
            }
        )
    }

    override fun insertItemWithReplaceStrategy(t: CurrencyPersist) {
        executor.execute(
            runnable = Runnable {
                dao.insertItemWithReplaceStrategy(t)
            }
        )
    }

    override fun insertListWithReplaceStrategy(t: List<CurrencyPersist>) {
        executor.execute(
            runnable = Runnable {
                dao.insertListWithReplaceStrategy(t)
            }
        )
    }

    override fun deleteItem(t: CurrencyPersist) {
        executor.execute(
            runnable = Runnable {
                dao.deleteItem(t)
            }
        )
    }
}