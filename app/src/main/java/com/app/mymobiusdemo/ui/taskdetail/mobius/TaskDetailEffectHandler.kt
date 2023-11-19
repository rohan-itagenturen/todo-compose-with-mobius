package com.app.mymobiusdemo.ui.taskdetail.mobius

import com.app.mymobiusdemo.data.TaskDataSource
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import java.util.concurrent.TimeUnit

object TaskDetailEffectHandler {

    operator fun invoke(dataSource: TaskDataSource): ObservableTransformer<TaskDetailEffect, TaskDetailEvent> {
        return RxMobius.subtypeEffectHandler<TaskDetailEffect, TaskDetailEvent>()
            .addTransformer(TaskDetailEffect.PerformLoadTask::class.java, loadTask(dataSource))
            .addTransformer(TaskDetailEffect.PerformUpdateTask::class.java, updateTask(dataSource))
            .build()
    }

    private fun loadTask(dataSource: TaskDataSource): ObservableTransformer<TaskDetailEffect.PerformLoadTask, TaskDetailEvent> {
        return ObservableTransformer<TaskDetailEffect.PerformLoadTask, TaskDetailEvent> {
            it.flatMap {
                val taskDto = dataSource.getTaskById(it.taskId)
                return@flatMap if (taskDto != null) {
                    Single.just(TaskDetailEvent.OnTaskLoadSuccess(taskDto)).toObservable()
                } else {
                    Single.just(TaskDetailEvent.OnTaskLoadError).toObservable()
                }

            }
        }
    }

    private fun updateTask(dataSource: TaskDataSource): ObservableTransformer<TaskDetailEffect.PerformUpdateTask, TaskDetailEvent> {
        return ObservableTransformer<TaskDetailEffect.PerformUpdateTask, TaskDetailEvent> {
            it.delay(1, TimeUnit.SECONDS)
                .flatMap {
                    val isUpdated = dataSource.updateTask(it.task)
                    return@flatMap if (isUpdated)
                        Single.just(TaskDetailEvent.OnTaskUpdateSuccess).toObservable()
                    else
                        Single.just(TaskDetailEvent.OnTaskUpdateError).toObservable()
                }
        }
    }
}