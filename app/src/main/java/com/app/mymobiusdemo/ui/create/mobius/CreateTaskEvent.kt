package com.app.mymobiusdemo.ui.create.mobius

import com.app.mymobiusdemo.data.TaskDto

sealed class CreateTaskEvent {
    data class RequestToCreateTask(val taskDto: TaskDto) : CreateTaskEvent()
    data object OnCreateTaskSuccess : CreateTaskEvent()
}