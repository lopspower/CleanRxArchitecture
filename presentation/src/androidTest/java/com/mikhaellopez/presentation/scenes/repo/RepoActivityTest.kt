package com.mikhaellopez.presentation.scenes.repo

import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.extensions.activityTestRule
import com.mikhaellopez.presentation.extensions.robot
import org.junit.Rule
import org.junit.Test

class RepoActivityTest {

    @get:Rule
    val activityRule = activityTestRule<RepoActivity>()

    private val repo = Repo(
        1,
        "name",
        "description",
        "http://repo.com",
        false,
        "userName"
    )

    @Test
    fun showData() {
        activityRule.robot(repo) {
            toolbar(repo.name)
            loading {
                content()
                progress()
                viewError(false)
                errorProgress(false)
            }
            data(repo) {
                content()
                data(repo)
                progress(false)
                viewError(false)
                errorProgress(false)
            }
        }
    }

    @Test
    fun showErrorAndRetry() {
        val myError = "My error"

        activityRule.robot(repo) {
            toolbar(repo.name)
            loading {
                content()
                progress()
            }
            error(myError) {
                viewError()
                textError(myError)
                errorProgress(false)
                content(false)
                progress(false)
            }
            retry {
                viewError()
                errorProgress()
                textError(myError)
                content(false)
                progress(false)
            }
        }
    }

}
