package com.mikhaellopez.data.net.api

import com.mikhaellopez.data.net.dto.RepoDTO

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("users/{userName}/repos")
    fun getListRepos(@Path("userName") userName: String): Single<List<RepoDTO>>

    @GET("repos/{userName}/{repoName}")
    fun getRepo(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): Single<RepoDTO>

}
