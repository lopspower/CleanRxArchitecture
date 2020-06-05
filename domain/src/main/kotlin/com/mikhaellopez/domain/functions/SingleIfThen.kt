package com.mikhaellopez.domain.functions

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleSource

/**
 * Copyright (C) 2020 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * When an [Single] subscribes, the condition is evaluated
 * and the appropriate [SingleSource] is subscribed to.
 * @param <T> the common value type of the Single
 */
class SingleIfThen<T>(
    private val condition: Boolean,
    private val then: SingleSource<out T>,
    private val orElse: SingleSource<out T>
) : Single<T>() {

    override fun subscribeActual(observer: SingleObserver<in T>) {
        if (condition) {
            then.subscribe(observer)
        } else {
            orElse.subscribe(observer)
        }
    }
}

