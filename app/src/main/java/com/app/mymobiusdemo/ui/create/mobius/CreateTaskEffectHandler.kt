package com.app.mymobiusdemo.ui.create.mobius

import com.app.mymobiusdemo.data.TaskDataSource
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

object CreateTaskEffectHandler {

    operator fun invoke(dataSource: TaskDataSource): ObservableTransformer<CreateTaskEffect, CreateTaskEvent> {
        return RxMobius.subtypeEffectHandler<CreateTaskEffect, CreateTaskEvent>()
            .addTransformer(CreateTaskEffect.PerformCreateTask::class.java, createTask(dataSource))
            .build()
    }

    private fun createTask(dataSource: TaskDataSource): ObservableTransformer<CreateTaskEffect.PerformCreateTask, CreateTaskEvent> {
        return ObservableTransformer<CreateTaskEffect.PerformCreateTask, CreateTaskEvent> {
            it.delay(1, TimeUnit.SECONDS)
                .flatMap {
                    dataSource.insertTask(it.taskDto)
                    return@flatMap Single.just(CreateTaskEvent.OnCreateTaskSuccess).toObservable()
                }
        }
    }
}