package com.mikhaellopez.presentation.scenes

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.extensions.enableTest
import com.mikhaellopez.presentation.extensions.getActivity
import com.mikhaellopez.presentation.scenes.repolist.RepoListActivity
import com.mikhaellopez.presentation.scenes.repolist.RepoListFragment
import com.mikhaellopez.presentation.scenes.repolist.RepoListRobot
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppUseCaseTest {

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
        Intents.init()
    }

    @After
    fun cleanup() {
        Intents.release()
        scenario.close()
    }

    @Test
    fun showDataAndOpenRepo() {
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
            data(repos) {
                clickAtPosition(0) {
                    data(repos[0]) {
                        clickOnMenuLink()
                    }
                }
            }
        }
    }

}