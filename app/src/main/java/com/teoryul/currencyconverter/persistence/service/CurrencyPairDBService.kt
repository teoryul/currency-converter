package com.teoryul.currencyconverter.persistence.service

import com.teoryul.currencyconverter.persistence.dao.CurrencyPairDao
import com.teoryul.currencyconverter.persistence.model.CurrencyPairPersist
import com.teoryul.currencyconverter.persistence.repository.CurrencyPairRepository
import com.teoryul.currencyconverter.utils.AppExecutor
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyPairDBService
@Inject constructor(
    private val dao: CurrencyPairDao,
    private val executor: AppExecutor
) : CurrencyPairRepository {

    override fun insertCurrencyPairWithAbortStrategy(currencyPairPersist: CurrencyPairPersist): Single<Long> {

        return Single.create { emitter ->
            try {
                emitter.onSuccess(dao.insertCurrencyPairWithAbortStrategy(currencyPairPersist))
            } catch (t: Throwable) {
                emitter.onError(t)
            }
        }
    }

    override fun getAllCurrencyPairs(): Single<List<CurrencyPairPersist>> {
        return dao.getAllCurrencyPairs()
    }

    override fun getAllCurrencyPairsSavedByUser(): Single<List<CurrencyPairPersist>> {
        return dao.getAllCurrencyPairsSavedByUser()
    }

    override fun insertItemWithIgnoreStrategy(t: CurrencyPairPersist) {
        executor.execute(
            runnable = Runnable {
                dao.insertItemWithIgnoreStrategy(t)
            }
        )
    }

    override fun insertItemWithReplaceStrategy(t: CurrencyPairPersist) {
        executor.execute(
            runnable = Runnable {
                dao.insertItemWithReplaceStrategy(t)
            }
        )
    }

    override fun insertListWithReplaceStrategy(t: List<CurrencyPairPersist>) {
        executor.execute(
            runnable = Runnable {
                dao.insertListWithReplaceStrategy(t)
            }
        )
    }

    override fun deleteItem(t: CurrencyPairPersist) {
        executor.execute(
            runnable = Runnable {
                dao.deleteItem(t)
            }
        )
    }
}