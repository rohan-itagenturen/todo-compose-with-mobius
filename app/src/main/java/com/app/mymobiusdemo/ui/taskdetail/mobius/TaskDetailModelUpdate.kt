package com.app.mymobiusdemo.ui.taskdetail.mobius

import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.functions.Consumer

class TaskDetailModelUpdate(val viewEffectConsumer: Consumer<TaskDetailViewEffect>) :
    Update<TaskDetailModel, TaskDetailEvent, TaskDetailEffect> {
    override fun update(
        model: TaskDetailModel,
        event: TaskDetailEvent
    ): Next<TaskDetailModel, TaskDetailEffect> {
        return when (event) {
            is TaskDetailEvent.RequestTaskLoad -> {
                Next.next(
                    model.copy(viewState = TaskDetailModel.ViewState.LOADING),
                    setOf(TaskDetailEffect.PerformLoadTask(event.taskId))
                )
            }

            is TaskDetailEvent.OnTaskLoadSuccess -> {
                Next.next(
                    model.copy(task = event.task, viewState = TaskDetailModel.ViewState.LOADED)
                )
            }

            is TaskDetailEvent.OnTaskLoadError -> {
                viewEffectConsumer.accept(TaskDetailViewEffect.ShowFeedback(TaskDetailViewEffect.FeedbackType.TaskLoadError))
                Next.noChange()
            }


            is TaskDetailEvent.RequestUpdateTask -> {
                Next.next(
                    model.copy(viewState = TaskDetailModel.ViewState.LOADING),
                    setOf(TaskDetailEffect.PerformUpdateTask(event.task))
                )
            }

            TaskDetailEvent.OnTaskUpdateError -> {
                viewEffectConsumer.accept(TaskDetailViewEffect.ShowFeedback(TaskDetailViewEffect.FeedbackType.UpdateTaskError))
                Next.next(
                    model.copy(viewState = TaskDetailModel.ViewState.LOADED)
                )
            }

            TaskDetailEvent.OnTaskUpdateSuccess -> {
                viewEffectConsumer.accept(TaskDetailViewEffect.TaskUpdated)
                Next.noChange()
            }
        }
    }
}