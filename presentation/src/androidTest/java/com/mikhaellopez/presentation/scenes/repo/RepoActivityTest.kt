package com.mikhaellopez.presentation.scenes.repo

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.extensions.enableTest
import com.mikhaellopez.presentation.extensions.getActivity
import org.junit.After
import org.junit.Before
import org.junit.Test

class RepoActivityTest {

    private val repo = Repo(
        1,
        "name",
        "description",
        "http://repo.com",
        false,
        "userName"
    )

    private lateinit var scenario: ActivityScenario<RepoActivity>

    private fun robot(func: RepoRobot.() -> Unit) {
        getActivity(scenario).also { activity ->
            val fragment =
                activity.supportFragmentManager.findFragmentById(R.id.container) as RepoFragment
            fragment.presenter.enableTest(fragment)
            RepoRobot.robot(func, fragment)
        }
    }

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(
            RepoActivity.newIntent(
                ApplicationProvider.getApplicationContext(),
                repo.id,
                repo.name,
                repo.userName
            )
        )
    }

    @After
    fun cleanup() {
        scenario.close()
    }

    @Test
    fun showData() {
        robot {
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

        robot {
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
