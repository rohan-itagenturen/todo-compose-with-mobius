package com.app.mymobiusdemo.ui.tasklist.mobius

import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.functions.Consumer

class TaskListModelUpdate(val viewEffectConsumer: Consumer<TaskListViewEffect>) :
    Update<TaskListModel, TaskListEvent, TaskListEffect> {
    override fun update(
        model: TaskListModel,
        event: TaskListEvent
    ): Next<TaskListModel, TaskListEffect> {
        return when (event) {
            is TaskListEvent.OnTaskLoaded -> {
                Next.next(model.copy(taskLists = event.tasks))
            }

            is TaskListEvent.OnTaskSelected -> {
                viewEffectConsumer.accept(TaskListViewEffect.ShowTaskDetails(event.taskId))
                Next.noChange()
            }

            TaskListEvent.OnCreateTask -> {
                viewEffectConsumer.accept(TaskListViewEffect.ShowCreateTask)
                Next.noChange()
            }

            is TaskListEvent.OnTaskDelete -> {
                Next.dispatch(setOf(TaskListEffect.PerformDeleteTask(event.taskId)))
            }

            is TaskListEvent.OnTaskDeleteSuccess -> {
                viewEffectConsumer.accept(TaskListViewEffect.ShowFeedback(TaskListViewEffect.FeedbackType.DeleteTaskSuccess))
                Next.dispatch(setOf(TaskListEffect.LoadTasks))
            }

            is TaskListEvent.OnTaskDeleteError -> {
                viewEffectConsumer.accept(TaskListViewEffect.ShowFeedback(TaskListViewEffect.FeedbackType.DeleteTaskError))
                Next.noChange()
            }
        }
    }
}