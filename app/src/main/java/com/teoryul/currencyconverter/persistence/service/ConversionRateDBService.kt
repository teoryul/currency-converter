package com.teoryul.currencyconverter.persistence.service

import com.teoryul.currencyconverter.persistence.dao.ConversionRateDao
import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import com.teoryul.currencyconverter.persistence.repository.ConversionRateRepository
import com.teoryul.currencyconverter.utils.AppExecutor
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversionRateDBService
@Inject constructor(
    private val dao: ConversionRateDao,
    private val executor: AppExecutor
) : ConversionRateRepository {

    override fun getAllConversionRatesForCurrencyPair(pair: String): Single<List<ConversionRatePersist>> {
        return dao.getAllConversionRatesForCurrencyPair(pair)
    }

    override fun getAllConversionRatesForCurrencyPairByDate(pair: String, date: Long):
            Single<List<ConversionRatePersist>> {
        return dao.getAllConversionRatesForCurrencyPairByDate(pair, date)
    }

    override fun getAllConversionRatesForCurrencyPairByDateRange(pair: String, startDate: Long, endDate: Long):
            Single<List<ConversionRatePersist>> {
        return dao.getAllConversionRatesForCurrencyPairByDateRange(pair, startDate, endDate)
    }

    override fun insertItemWithIgnoreStrategy(t: ConversionRatePersist) {
        executor.execute(
            runnable = Runnable {
                dao.insertItemWithIgnoreStrategy(t)
            }
        )
    }

    override fun insertItemWithReplaceStrategy(t: ConversionRatePersist) {
        executor.execute(
            runnable = Runnable {
                dao.insertItemWithReplaceStrategy(t)
            }
        )
    }

    override fun insertListWithReplaceStrategy(t: List<ConversionRatePersist>) {
        executor.execute(
            runnable = Runnable {
                dao.insertListWithReplaceStrategy(t)
            }
        )
    }

    override fun deleteItem(t: ConversionRatePersist) {
        executor.execute(
            runnable = Runnable {
                dao.deleteItem(t)
            }
        )
    }
}