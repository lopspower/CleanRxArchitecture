package com.mikhaellopez.data.net.api

import android.content.Context
import com.google.gson.Gson
import com.mikhaellopez.data.net.OkHttpClientFactoryTest
import com.mikhaellopez.data.net.RetrofitFactory
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHubApiTest {

    private val context = mock<Context>()
    private val retrofit = RetrofitFactory.getRetrofit(context, Gson(), OkHttpClientFactoryTest())

    private val service: GitHubApi
        get() = retrofit.create(GitHubApi::class.java)

    @Test
    fun getListReposWebservice() {
        service.getListRepos("lopspower").test()
            .assertNoErrors()
    }

    @Test
    fun getRepoWebservice() {
        service.getRepo("lopspower", "CircularImageView").test()
            .assertNoErrors()
    }
}
