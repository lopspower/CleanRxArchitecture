package com.mikhaellopez.presentation.scenes.repolist

import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.scenes.base.view.LoadDataView

import io.reactivex.rxjava3.core.Observable

interface RepoListView : LoadDataView<RepoListViewModel> {

    fun intentLoadData(): Observable<String>

    fun intentRefreshData(): Observable<String>

    fun intentErrorRetry(): Observable<String>

    fun intentFavorite(): Observable<Pair<Int, Repo>>

    fun openRepo(): Observable<Pair<Repo, String>>

}