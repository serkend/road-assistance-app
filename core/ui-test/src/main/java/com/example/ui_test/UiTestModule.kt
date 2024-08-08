package com.example.ui_test

import com.example.navigation.FlowNavigator
import com.example.navigation.NavigationModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NavigationModule::class]
)
object UiTestModule {

    @Provides
    @Singleton
    fun provideFlowNavigator(): FlowNavigator {
        return mockk(relaxed = true)
    }

}