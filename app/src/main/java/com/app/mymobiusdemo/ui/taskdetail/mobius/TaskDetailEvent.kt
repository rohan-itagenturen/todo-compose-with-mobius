package com.app.mymobiusdemo.ui.taskdetail.mobius

import com.app.mymobiusdemo.data.TaskDto

sealed class TaskDetailEvent {

    data class RequestTaskLoad(val taskId: Long) : TaskDetailEvent()
    data class OnTaskLoadSuccess(val task: TaskDto) : TaskDetailEvent()
    data object OnTaskLoadError : TaskDetailEvent()
    data class RequestUpdateTask(val task: TaskDto) : TaskDetailEvent()
    data object OnTaskUpdateSuccess : TaskDetailEvent()
    data object OnTaskUpdateError : TaskDetailEvent()

}