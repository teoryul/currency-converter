package com.teoryul.currencyconverter.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItemWithIgnoreStrategy(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemWithReplaceStrategy(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListWithReplaceStrategy(t: List<T>)

    @Delete
    fun deleteItem(t: T)
}