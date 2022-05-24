package com.mikhaellopez.presentation.extensions

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import java.util.concurrent.atomic.AtomicReference

fun <T : Activity?> getActivity(activityScenario: ActivityScenario<T>): T {
    val activityRef: AtomicReference<T> = AtomicReference()
    activityScenario.onActivity(activityRef::set)
    return activityRef.get()
}