package com.teoryul.currencyconverter.persistence.repository

interface BaseRepository<T> {

    fun insertItemWithIgnoreStrategy(t: T)

    fun insertItemWithReplaceStrategy(t: T)

    fun insertListWithReplaceStrategy(t: List<T>)

    fun deleteItem(t: T)
}