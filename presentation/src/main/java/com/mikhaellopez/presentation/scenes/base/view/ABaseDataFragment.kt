package com.mikhaellopez.presentation.scenes.base.view

import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.extensions.findViewById

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
abstract class ABaseDataFragment<VB : ViewBinding> : ABaseFragment<VB>() {

    protected val btnErrorRetry: View?
        get() = findViewById<View>(R.id.btnErrorRetry)

    //region RENDER
    protected open fun showLoading(visible: Boolean) {
        findViewById<View>(R.id.progress)?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun showRefreshingLoading(swipeRefreshLayout: SwipeRefreshLayout, visible: Boolean) {
        swipeRefreshLayout.isRefreshing = visible
    }

    protected fun showRetryLoading(visible: Boolean) {
        btnErrorRetry?.isClickable = !visible
        findViewById<View>(R.id.errorProgress)?.visibility =
            if (visible) View.VISIBLE else View.INVISIBLE
    }

    protected fun showContent(content: View, visible: Boolean) {
        content.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun showError(visible: Boolean) {
        findViewById<View>(R.id.viewError)?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun renderError(messageError: String?) {
        messageError?.also { findViewById<TextView>(R.id.textErrorDescription)?.text = it }
    }

    protected fun renderSnack(message: String?) {
        message?.also {
            if (activity != null) Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
    //endregion

}
