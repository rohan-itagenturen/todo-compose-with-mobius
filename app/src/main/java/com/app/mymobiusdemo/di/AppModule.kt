package com.app.mymobiusdemo.di

import com.app.mymobiusdemo.data.TaskDataSource
import com.spotify.mobius.android.runners.MainThreadWorkRunner
import com.spotify.mobius.runners.WorkRunner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesMainThreadWorkRunner(): WorkRunner {
        return MainThreadWorkRunner.create()
    }

    @Provides
    @Singleton
    fun providesTaskDataSource(): TaskDataSource {
        return TaskDataSource()
    }
}