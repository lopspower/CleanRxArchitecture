package com.mikhaellopez.presentation.di.components

import android.app.Application
import com.mikhaellopez.data.di.components.DataComponent
import com.mikhaellopez.presentation.di.PerApplication
import com.mikhaellopez.presentation.di.modules.ApplicationModule
import com.mikhaellopez.presentation.di.modules.UseCaseModule
import dagger.BindsInstance
import dagger.Component

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * A component whose lifetime is the life of the application.
 */
@PerApplication // Constraints this component to one-per-application or unscoped bindings.
@Component(
    dependencies = [(DataComponent::class)],
    modules = [(ApplicationModule::class), (UseCaseModule::class)]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            dataComponent: DataComponent
        ): ApplicationComponent
    }

    fun activityComponent(): ActivityComponent.Factory

}
