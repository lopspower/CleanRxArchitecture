package com.mikhaellopez.presentation.scenes.repolist

import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.extensions.activityTestRule
import com.mikhaellopez.presentation.extensions.robot
import org.junit.Rule
import org.junit.Test

class RepoListActivityTest {

    @get:Rule
    val activityRule = activityTestRule<RepoListActivity>()

    @Test
    fun showData() {
        val repos = listOf(
            Repo(
                1,
                "name",
                "description",
                "http://repo.com",
                false,
                "userName"
            )
        )

        activityRule.robot {
            toolbar()
            loading {
                content()
                progress()
                viewError(false)
                errorProgress(false)
            }
            data(repos) {
                content()
                data(repos[0])
                progress(false)
                viewError(false)
                errorProgress(false)
            }
        }
    }

    @Test
    fun showErrorAndRetry() {
        val myError = "My error"

        activityRule.robot {
            toolbar()
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
