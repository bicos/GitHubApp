package com.ravy.common_ui.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

inline fun <T> LiveData<T>.filter(crossinline filter: (T?) -> Boolean): LiveData<T> {
        return MediatorLiveData<T>().apply {
            addSource(this@filter) {
                if (filter(it)) {
                    this.value = it
                }
            }
        }
    }