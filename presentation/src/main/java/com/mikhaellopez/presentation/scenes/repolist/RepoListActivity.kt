package com.mikhaellopez.presentation.scenes.repolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.databinding.ActivityLayoutToLoadFragmentBinding
import com.mikhaellopez.presentation.extensions.addFragment
import com.mikhaellopez.presentation.extensions.enableToolbar
import com.mikhaellopez.presentation.scenes.base.view.ABaseActivity

class RepoListActivity : ABaseActivity<ActivityLayoutToLoadFragmentBinding>() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, RepoListActivity::class.java)
    }

    // View Binding
    override val bindingInflater: (LayoutInflater) -> ActivityLayoutToLoadFragmentBinding
        get() = { inflater -> ActivityLayoutToLoadFragmentBinding.inflate(inflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.Base_Theme)
        super.onCreate(savedInstanceState)
        initializeActivity(savedInstanceState)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        enableToolbar()
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.container, RepoListFragment.newInstance())
        }
    }
}
