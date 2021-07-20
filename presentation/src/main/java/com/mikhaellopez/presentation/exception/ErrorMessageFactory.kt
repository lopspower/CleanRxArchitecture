package com.mikhaellopez.presentation.exception

import android.content.Context
import com.mikhaellopez.domain.exception.NoConnectedException
import com.mikhaellopez.domain.exception.PersistenceException
import com.mikhaellopez.presentation.R
import timber.log.Timber
import javax.inject.Inject

/**
 * Copyright (C) 2021 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Factory used to create error messages from an Exception as a condition.
 */
open class ErrorMessageFactory
@Inject internal constructor(private val context: Context) {

    /**
     * Creates a String representing an error message.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return [String] an error message.
     */
    open fun getError(exception: Throwable?): String =
        exception?.let {
            when (it) {
                is NoConnectedException -> context.getString(R.string.error_no_connection)
                is PersistenceException -> context.getString(R.string.error_persistence)
                else -> context.getString(R.string.error_generic)
            }.apply { Timber.e(it) }
        } ?: getGenericError()

    private fun getGenericError() = context.getString(R.string.error_generic)
}
