package com.mikhaellopez.presentation.scenes.base.view

import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.presentation.AndroidApplication
import com.mikhaellopez.presentation.di.components.ActivityComponent
import com.mikhaellopez.presentation.di.components.ApplicationComponent

abstract class ABaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    private val applicationComponent: ApplicationComponent by lazy {
        (application as AndroidApplication).appComponent
    }

    val activityComponent: ActivityComponent by lazy {
        applicationComponent.activityComponent().create(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle toolbar back arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}