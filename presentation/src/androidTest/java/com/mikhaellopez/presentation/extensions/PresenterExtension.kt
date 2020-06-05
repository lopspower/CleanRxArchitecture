package com.mikhaellopez.presentation.extensions

import com.mikhaellopez.presentation.scenes.base.presenter.APresenter
import com.mikhaellopez.presentation.scenes.base.view.LoadDataView

fun <P : APresenter<V, VM>, V : LoadDataView<VM>, VM> P.enableTest(view: V) {
    detach()
    testMode = true
    attach(view)
}