package com.mikhaellopez.presentation.extensions

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.scenes.repo.RepoActivity
import com.mikhaellopez.presentation.scenes.repo.RepoFragment
import com.mikhaellopez.presentation.scenes.repo.RepoRobot
import com.mikhaellopez.presentation.scenes.repolist.RepoListActivity
import com.mikhaellopez.presentation.scenes.repolist.RepoListFragment
import com.mikhaellopez.presentation.scenes.repolist.RepoListRobot

fun ActivityTestRule<RepoListActivity>.robot(func: RepoListRobot.() -> Unit) {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val activity = launchActivity(RepoListActivity.newIntent(context))
    val fragment =
        activity.supportFragmentManager.findFragmentById(R.id.container) as RepoListFragment
    fragment.presenter.enableTest(fragment)
    RepoListRobot.robot(func, fragment)
}

fun ActivityTestRule<RepoActivity>.robot(repo: Repo, func: RepoRobot.() -> Unit) {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val activity =
        launchActivity(RepoActivity.newIntent(context, repo.id, repo.name, repo.userName))
    val fragment = activity.supportFragmentManager.findFragmentById(R.id.container) as RepoFragment
    fragment.presenter.enableTest(fragment)
    RepoRobot.robot(func, fragment)
}

inline fun <reified T : Activity> activityTestRule(
    initialTouchMode: Boolean = false,
    launchActivity: Boolean = false
): ActivityTestRule<T> =
    ActivityTestRule(T::class.java, initialTouchMode, launchActivity)