package com.mikhaellopez.presentation.scenes.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mikhaellopez.presentation.AndroidApplication
import com.mikhaellopez.presentation.di.components.ActivityComponent
import com.mikhaellopez.presentation.di.components.ApplicationComponent

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Base [Fragment] class for every fragment in this application.
 */
abstract class ABaseFragment<VB : ViewBinding> : Fragment() {

    protected val appComponent: ApplicationComponent by lazy { (requireActivity().application as AndroidApplication).appComponent }

    protected val activityComponent: ActivityComponent by lazy { (activity as ABaseActivity<*>).activityComponent }

    override fun getContext(): Context = requireContext()

    private var _binding: VB? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
