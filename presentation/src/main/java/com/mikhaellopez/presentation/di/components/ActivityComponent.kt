package com.mikhaellopez.presentation.di.components

import android.app.Activity
import com.mikhaellopez.presentation.di.PerActivity
import com.mikhaellopez.presentation.di.modules.ActivityModule
import com.mikhaellopez.presentation.scenes.repo.RepoFragment
import com.mikhaellopez.presentation.scenes.repolist.RepoListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: Activity): ActivityComponent
    }

    //region Inject
    fun inject(fragment: RepoListFragment)

    fun inject(fragment: RepoFragment)
    //endregion

}
