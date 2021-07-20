package com.mikhaellopez.domain.usecases.base

import io.reactivex.rxjava3.core.Single

/**
 * Copyright (C) 2021 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
abstract class SingleUseCase<R, in P>
constructor(
    private val useCaseScheduler: UseCaseScheduler?,
    private val logger: Logger?
) : UseCase<Single<R>, P>(logger) {

    override fun execute(param: P, fromUseCase: Boolean): Single<R> =
        super.execute(param, fromUseCase)
            .compose { transformer ->
                useCaseScheduler?.let {
                    if (fromUseCase) transformer
                    else transformer.subscribeOn(it.run).observeOn(it.post)
                } ?: transformer
            }
            .doOnError { logger?.logError { it } }
            .doOnSuccess { logger?.log { "${javaClass.simpleName} : $param => $it" } }

}
