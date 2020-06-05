package com.mikhaellopez.presentation.scenes.repo

import android.os.Bundle
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import com.mikhaellopez.data.helper.TimberWrapper
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.usecases.GetRepo
import com.mikhaellopez.domain.usecases.RefreshRepo
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.extensions.build
import com.mikhaellopez.presentation.extensions.getLongArg
import com.mikhaellopez.presentation.extensions.getStringArg
import com.mikhaellopez.presentation.scenes.base.view.ABaseDataFragment
import com.mikhaellopez.presentation.scenes.base.view.ContentState
import com.mikhaellopez.presentation.scenes.base.view.LoadingState
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.repo_fragment.*
import javax.inject.Inject

class RepoFragment : ABaseDataFragment(R.layout.repo_fragment), RepoView {

    companion object {
        private const val ARGS_REPO_ID = "args_repo_id"
        private const val ARGS_REPO_NAME = "args_repo_name"
        private const val ARGS_USER_NAME = "args_user_name"

        fun newInstance(repoId: Long, repoName: String, userName: String): RepoFragment =
            RepoFragment().build {
                putLong(ARGS_REPO_ID, repoId)
                putString(ARGS_REPO_NAME, repoName)
                putString(ARGS_USER_NAME, userName)
            }
    }

    @Inject
    lateinit var presenter: RepoPresenter

    // Properties
    private val repoId: Long by lazy { getLongArg(ARGS_REPO_ID) }
    private val repoName: String by lazy { getStringArg(ARGS_REPO_NAME) }
    private val userName: String by lazy { getStringArg(ARGS_USER_NAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }

    //region INTENTS
    override fun intentLoadData(): Observable<GetRepo.Param> =
        Observable.just(GetRepo.Param(repoId, repoName, userName))

    override fun intentRefreshData(): Observable<RefreshRepo.Param> =
        swipeRefreshLayout.refreshes()
            .map { RefreshRepo.Param(repoId, repoName, userName) }

    override fun intentErrorRetry(): Observable<GetRepo.Param> =
        btnErrorRetry.clicks().map { GetRepo.Param(repoId, repoName, userName) }

    override fun intentActionLink(): Observable<Unit> =
        (activity as RepoActivity).intentActionLink
    //endregion

    //region RENDER
    override fun render(viewModel: RepoViewModel) {
        TimberWrapper.d { "render: $viewModel" }

        showLoading(viewModel.loadingState == LoadingState.LOADING)
        showRefreshingLoading(swipeRefreshLayout, false)
        showRetryLoading(viewModel.loadingState == LoadingState.RETRY)
        showContent(content, viewModel.contentState == ContentState.CONTENT)
        showError(viewModel.contentState == ContentState.ERROR)

        renderData(viewModel.data)
        renderError(viewModel.errorMessage)
        renderSnack(viewModel.snackMessage)
    }

    private fun renderData(repo: Repo?) {
        repo?.also {
            textRepoName.text = it.name
            textRepoDescription.text = it.description
        }
    }
    //endregion
}
