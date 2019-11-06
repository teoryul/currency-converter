package com.teoryul.currencyconverter.utils

import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppExecutor @Inject constructor(private val executor: Executor) {

    fun execute(runnable: Runnable) {
        executor.execute(runnable)
    }
}