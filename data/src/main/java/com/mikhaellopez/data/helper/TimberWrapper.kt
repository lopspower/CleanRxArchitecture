package com.mikhaellopez.data.helper

import timber.log.Timber

object TimberWrapper {

    fun d(message: () -> String) {
        if (Timber.treeCount() > 0) {
            try {
                Timber.d(message())
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    fun e(throwable: () -> Throwable) {
        if (Timber.treeCount() > 0) {
            try {
                Timber.d(throwable())
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

}