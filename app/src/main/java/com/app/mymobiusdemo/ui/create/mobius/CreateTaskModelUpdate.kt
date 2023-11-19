package com.app.mymobiusdemo.ui.create.mobius

import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.functions.Consumer

class CreateTaskModelUpdate(val viewEffectConsumer: Consumer<CreateTaskViewEffect>) :
    Update<CreateTaskModel, CreateTaskEvent, CreateTaskEffect> {
    override fun update(
        model: CreateTaskModel,
        event: CreateTaskEvent
    ): Next<CreateTaskModel, CreateTaskEffect> {
        return when (event) {
            is CreateTaskEvent.RequestToCreateTask -> {
                Next.next(
                    model.copy(viewState = CreateTaskModel.ViewState.LOADING),
                    setOf(CreateTaskEffect.PerformCreateTask(event.taskDto))
                )
            }
            CreateTaskEvent.OnCreateTaskSuccess -> {
                viewEffectConsumer.accept(CreateTaskViewEffect.TaskCreated)
                Next.noChange()
            }
        }
    }
}