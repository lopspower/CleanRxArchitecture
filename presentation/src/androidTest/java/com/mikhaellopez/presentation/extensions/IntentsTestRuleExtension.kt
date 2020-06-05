package com.mikhaellopez.presentation.extensions

import android.app.Activity
import androidx.test.espresso.intent.rule.IntentsTestRule

inline fun <reified T : Activity> intentsTestRule(
    initialTouchMode: Boolean = false,
    launchActivity: Boolean = false
): IntentsTestRule<T> =
    IntentsTestRule(T::class.java, initialTouchMode, launchActivity)