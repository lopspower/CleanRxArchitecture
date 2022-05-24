package com.mikhaellopez.domain.usecases.base

import io.reactivex.rxjava3.core.Scheduler

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
data class UseCaseScheduler(val run: Scheduler, val post: Scheduler)
