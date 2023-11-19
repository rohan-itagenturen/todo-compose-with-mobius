package com.app.mymobiusdemo.ui.tasklist.mobius

import com.app.mymobiusdemo.data.TaskDataSource
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import io.reactivex.Single

object TaskListEffectHandler {
    operator fun invoke(dataSource: TaskDataSource) : ObservableTransformer<TaskListEffect, TaskListEvent> {
        return RxMobius.subtypeEffectHandler<TaskListEffect, TaskListEvent>()
            .addTransformer(TaskListEffect.LoadTasks::class.java, loadTasks(dataSource))
            .addTransformer(TaskListEffect.PerformDeleteTask::class.java, deleteTask(dataSource))
            .build()
    }

    private fun loadTasks(dataSource: TaskDataSource): ObservableTransformer<TaskListEffect.LoadTasks, TaskListEvent> {
        return ObservableTransformer<TaskListEffect.LoadTasks, TaskListEvent> {
            it.flatMap {
                val taskList = dataSource.getTasks()
                return@flatMap Single.just(TaskListEvent.OnTaskLoaded(taskList)).toFlowable().toObservable()
            }
        }
    }

    private fun deleteTask(dataSource: TaskDataSource): ObservableTransformer<TaskListEffect.PerformDeleteTask, TaskListEvent> {
        return ObservableTransformer<TaskListEffect.PerformDeleteTask, TaskListEvent> {
            it.flatMap {
                val isDeleted = dataSource.deleteTaskById(it.taskId)
                return@flatMap if (isDeleted)
                    Single.just(TaskListEvent.OnTaskDeleteSuccess).toObservable()
                else
                    Single.just(TaskListEvent.OnTaskDeleteError).toObservable()
            }
        }
    }
}