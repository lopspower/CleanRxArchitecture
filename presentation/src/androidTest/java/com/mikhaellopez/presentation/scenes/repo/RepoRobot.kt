package com.mikhaellopez.presentation.scenes.repo

import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R.id.*
import com.mikhaellopez.presentation.extensions.checkChildWithText
import com.mikhaellopez.presentation.extensions.checkIsDisplayed
import com.mikhaellopez.presentation.extensions.checkWithText
import com.mikhaellopez.presentation.scenes.base.Robot

class RepoRobot(view: RepoView? = null) : Robot<RepoViewModel>(view) {

    companion object {
        fun robot(func: RepoRobot.() -> Unit, view: RepoView? = null) {
            RepoRobot(view).apply { func() }
        }
    }

    //region ViewModel
    fun loading(func: RepoRobot.() -> Unit) =
        render(this, RepoViewModel.createLoading(), func)

    fun data(data: Repo, func: RepoRobot.() -> Unit) =
        render(this, RepoViewModel.createData(data), func)

    fun error(error: String, func: RepoRobot.() -> Unit) =
        render(this, RepoViewModel.createError(error), func)

    fun retry(func: RepoRobot.() -> Unit) =
        render(this, RepoViewModel.createRetryLoading(), func)
    //endregion

    //region View
    fun toolbar(title: String) {
        toolbar.checkIsDisplayed()
        toolbar.checkChildWithText(title)
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
        textRepoName.checkWithText(item.name)
        textRepoDescription.checkWithText(item.description)
    }
    //endregion

}