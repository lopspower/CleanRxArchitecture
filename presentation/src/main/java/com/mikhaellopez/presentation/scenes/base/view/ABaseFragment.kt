package com.mikhaellopez.presentation.scenes.base.view

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.mikhaellopez.presentation.AndroidApplication
import com.mikhaellopez.presentation.di.components.ActivityComponent
import com.mikhaellopez.presentation.di.components.ApplicationComponent

/**
 * Copyright (C) 2020 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Base [Fragment] class for every fragment in this application.
 */
abstract class ABaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected val appComponent: ApplicationComponent by lazy { (requireActivity().application as AndroidApplication).appComponent }

    protected val activityComponent: ActivityComponent by lazy { (activity as ABaseActivity).activityComponent }

    override fun getContext(): Context = requireContext()
}
