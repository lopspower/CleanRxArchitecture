package com.mikhaellopez.domain.usecases.base

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
interface Logger {
    fun log(message: () -> String)
    fun logError(throwable: () -> Throwable)
}