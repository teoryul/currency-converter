package com.teoryul.currencyconverter.event

import android.os.Bundle

class Event<out T>(private val content: T, val bundle: Bundle? = null) {

    var hasBeenHandled = false
        private set

    /**
     * Returns the content if the boolean flag is currently false.
     * Returns null if true.
     */
    fun getContent(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
}

enum class NavigationAction(val value: Int) {
    POP_BACK_STACK(-1)
}