package com.mikhaellopez.presentation.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mikhaellopez.presentation.R

/**
 * Adds a [Fragment] to this activity's layout.
 * @param containerViewId The container view to where add the fragment.
 * @param fragment The fragment to be added.
 */
fun AppCompatActivity.addFragment(containerViewId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
}

fun AppCompatActivity.enableToolbar(enableBack: Boolean = false, title: String? = null) {
    setSupportActionBar(findViewById(R.id.toolbar))
    title?.also { supportActionBar?.title = title }
    if (enableBack) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

fun AppCompatActivity.getLongExtra(key: String): Long = intent!!.extras!!.getLong(key)

fun AppCompatActivity.getBooleanExtra(key: String): Boolean = intent!!.extras!!.getBoolean(key)

fun AppCompatActivity.getStringExtra(key: String): String = intent!!.extras!!.getString(key)!!