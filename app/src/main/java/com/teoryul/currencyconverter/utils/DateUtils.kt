package com.teoryul.currencyconverter.utils

import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat(SIMPLE_DATE_FORMAT_PATTERN_DATE)
private val yearFormat = SimpleDateFormat(SIMPLE_DATE_FORMAT_PATTERN_YEAR)
private val monthFormat = SimpleDateFormat(SIMPLE_DATE_FORMAT_PATTERN_MONTH)
private val dayFormat = SimpleDateFormat(SIMPLE_DATE_FORMAT_PATTERN_DAY)
private val dayAndMonthFormat = SimpleDateFormat(SIMPLE_DATE_FORMAT_PATTERN_DAY_AND_MONTH)

object DateUtils {

    private var formattedCurrentTime: Long = 0L

    fun dateStringToLongTime(date: String): Long = dateFormat.parse(date).time

    fun getYearFromDateString(date: String): String = yearFormat.format(dateFormat.parse(date))

    fun getMonthFromDateString(date: String): String = monthFormat.format(dateFormat.parse(date))

    fun getDayFromDateString(date: String): String = dayFormat.format(dateFormat.parse(date))

    fun longTimeToDateString(time: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = time

        return dateFormat.format(date.time)
    }

    fun getYearFromLongTime(time: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = time

        return yearFormat.format(date.time)
    }

    fun getMonthFromLongTime(time: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = time

        return monthFormat.format(date.time)
    }

    fun getDayFromLongTime(time: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = time

        return dayFormat.format(date.time)
    }

    fun getDayAndMonthFromLongTime(time: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = time

        return dayAndMonthFormat.format(date.time)
    }

    fun getCurrentTimeInMillis(): Long = Calendar.getInstance().timeInMillis

    fun getFormattedCurrentTime(): Long {
        if (formattedCurrentTime == 0L) {
            formattedCurrentTime =
                dateStringToLongTime(
                    longTimeToDateString(
                        getCurrentTimeInMillis()
                    )
                )
        }

        return formattedCurrentTime
    }

    /**
     * Returns the date as string that is 8 days before the current date
     * which is used to request a date range of conversion rates from the API.
     */
    fun getDateRangeStartDateAsString(time: Long): String = longTimeToDateString(getDateRangeStartDateAsLong(time))

    fun getDateRangeStartDateAsLong(time: Long): Long = time - (ONE_DAY_AS_MILLIS * HISTORICAL_DATA_DATE_RANGE)

    /**
     * True - if there are at least 8 consecutive dates at the end of the list. False - otherwise.
     * NOTE: List is already sorted when returned by the db or api in ascending order with the oldest date at 0 index.
     */
    fun areDatesConsecutive(dates: List<Long>): Boolean {
        if (dates.size < HISTORICAL_DATA_DATE_RANGE) {

            return false
        } else {
            val eightDaysSubList =
                if (dates.size > HISTORICAL_DATA_DATE_RANGE) {
                    dates.subList(dates.size - HISTORICAL_DATA_DATE_RANGE, dates.size)
                } else {
                    dates
                }
            for (i in 1 until eightDaysSubList.size) {
                if (ONE_DAY_AS_MILLIS != eightDaysSubList[i] - eightDaysSubList[i - 1]) {

                    return false
                }
            }

            return true
        }
    }
}