package com.mikhaellopez.presentation.scenes.repolist

import android.content.Intent
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.R.id.*
import com.mikhaellopez.presentation.extensions.*
import com.mikhaellopez.presentation.scenes.base.Robot
import com.mikhaellopez.presentation.scenes.repo.RepoActivity
import com.mikhaellopez.presentation.scenes.repo.RepoRobot
import org.hamcrest.Matchers.allOf

class RepoListRobot(view: RepoListView? = null) : Robot<RepoListViewModel>(view) {

    companion object {
        fun robot(func: RepoListRobot.() -> Unit, view: RepoListView? = null) {
            RepoListRobot(view).apply { func() }
        }
    }

    //region ViewModel
    fun loading(func: RepoListRobot.() -> Unit) =
        render(this, RepoListViewModel.createLoading(), func)

    fun data(data: List<Repo>, func: RepoListRobot.() -> Unit) =
        render(this, RepoListViewModel.createData(data), func)

    fun error(error: String, func: RepoListRobot.() -> Unit) =
        render(this, RepoListViewModel.createError(error), func)

    fun retry(func: RepoListRobot.() -> Unit) =
        render(this, RepoListViewModel.createRetryLoading(), func)
    //endregion

    //region View
    fun toolbar() {
        toolbar.checkIsDisplayed()
        toolbar.checkChildWithText(R.string.app_name)
    }

    fun content(displayed: Boolean = true) {
        content.checkIsDisplayed(displayed)
    }

    fun progress(displayed: Boolean = true) {
        progress.checkIsDisplayed(displayed)
    }

    fun viewError(displayed: Boolean = true) {
        viewError.checkIsDisplayed(displayed)
    }

    fun errorProgress(displayed: Boolean = true) {
        errorProgress.checkIsDisplayed(displayed)
    }

    fun textError(text: String) {
        textErrorDescription.checkWithText(text)
    }

    fun data(item: Repo) {
        recyclerView.checkAdapterItemContent(0, item.name)
        recyclerView.checkAdapterItemContent(0, item.description)
    }
    //endregion

    //region Interactions
    fun clickAtPosition(position: Int, func: RepoRobot.() -> Unit) {
        recyclerView.performScrollRecyclerTo(position)
        recyclerView.performActionOnRecyclerItemAtPosition<ReposAdapter.ViewHolder>(
            position,
            click()
        )
        intended(hasComponent(RepoActivity::class.java.name))
        RepoRobot.robot(func)
    }

    fun clickOnMenuLink() {
        action_link.performClick()
        intended(allOf(hasAction(Intent.ACTION_VIEW)))
    }
    //endregion

}