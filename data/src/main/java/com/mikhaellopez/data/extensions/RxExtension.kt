package com.mikhaellopez.data.extensions

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

fun <T> Observable<T>.shareReplay(): Observable<T> = replay(1).refCount()

fun <T> Observable<T>.startWithSingle(data: T): Observable<T> = startWith(Single.just(data))