package com.mikhaellopez.presentation.scenes.repolist

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.extensions.enableTest
import com.mikhaellopez.presentation.extensions.getActivity
import org.junit.After
import org.junit.Before
import org.junit.Test

class RepoListActivityTest {

    private lateinit var scenario: ActivityScenario<RepoListActivity>

    private fun robot(func: RepoListRobot.() -> Unit) {
        getActivity(scenario).also { activity ->
            val fragment =
                activity.supportFragmentManager.findFragmentById(R.id.container) as RepoListFragment
            fragment.presenter.enableTest(fragment)
            RepoListRobot.robot(func, fragment)
        }
    }

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(
            RepoListActivity.newIntent(ApplicationProvider.getApplicationContext())
        )
    }

    @After
    fun cleanup() {
        scenario.close()
    }

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

        robot {
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

        robot {
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
