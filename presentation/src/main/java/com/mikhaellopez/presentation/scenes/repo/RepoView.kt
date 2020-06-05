package com.mikhaellopez.presentation.scenes.repo

import com.mikhaellopez.domain.usecases.GetRepo
import com.mikhaellopez.domain.usecases.RefreshRepo
import com.mikhaellopez.presentation.scenes.base.view.LoadDataView
import io.reactivex.rxjava3.core.Observable

interface RepoView : LoadDataView<RepoViewModel> {

    fun intentLoadData(): Observable<GetRepo.Param>

    fun intentRefreshData(): Observable<RefreshRepo.Param>

    fun intentErrorRetry(): Observable<GetRepo.Param>

    fun intentActionLink(): Observable<Unit>

}