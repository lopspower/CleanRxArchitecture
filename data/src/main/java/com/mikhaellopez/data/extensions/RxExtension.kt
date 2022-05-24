package com.mikhaellopez.data.extensions

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

fun <T : Any> Observable<T>.shareReplay(): Observable<T> = replay(1).refCount()

fun <T : Any> Observable<T>.startWithSingle(data: T): Observable<T> = startWith(Single.just(data))