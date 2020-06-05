package com.mikhaellopez.presentation.scenes

import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.extensions.intentsTestRule
import com.mikhaellopez.presentation.extensions.robot
import com.mikhaellopez.presentation.scenes.repolist.RepoListActivity
import org.junit.Rule
import org.junit.Test

class AppUseCaseTest {

    @get:Rule
    val activityRule = intentsTestRule<RepoListActivity>()

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

        activityRule.robot {
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