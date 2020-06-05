package com.mikhaellopez.presentation.scenes.base.presenter

import androidx.annotation.VisibleForTesting
import com.mikhaellopez.presentation.exception.ErrorMessageFactory
import com.mikhaellopez.presentation.scenes.base.view.LoadDataView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Copyright (C) 2020 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Abstract class representing a Presenter in a model view presenter (MVI) pattern.
 */
abstract class APresenter<in View : LoadDataView<ViewModel>, ViewModel>(private val errorMessageFactory: ErrorMessageFactory) {

    protected val composite = CompositeDisposable()

    @set:VisibleForTesting
    var testMode: Boolean = false

    abstract fun attach(view: View)

    protected fun subscribeViewModel(view: View, vararg observables: Observable<ViewModel>) {
        if (!testMode) {
            composite.add(Observable.mergeArray(*observables).subscribe { view.render(it) })
        }
    }

    protected fun getErrorMessage(error: Throwable): String = errorMessageFactory.getError(error)

    fun detach() {
        composite.clear()
    }

}
