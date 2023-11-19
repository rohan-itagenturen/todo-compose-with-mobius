package com.app.mymobiusdemo.ui.tasklist.mobius

import com.app.mymobiusdemo.data.TaskDto

sealed class TaskListEvent {

    data class OnTaskLoaded(val tasks: List<TaskDto>) : TaskListEvent()
    data object OnCreateTask : TaskListEvent()
    data class OnTaskSelected(val taskId: Long) : TaskListEvent()
    data class OnTaskDelete(val taskId: Long) : TaskListEvent()
    data object OnTaskDeleteSuccess : TaskListEvent()
    data object OnTaskDeleteError : TaskListEvent()

}