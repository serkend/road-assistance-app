package com.example.app

import androidx.appcompat.app.AppCompatActivity
import com.example.core.common.navigation.FlowNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideFlowNavigator(@ActivityContext activity: AppCompatActivity): FlowNavigator {
        return activity as FlowNavigator
    }

}