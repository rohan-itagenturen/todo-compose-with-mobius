package com.app.mymobiusdemo.ui.tasklist.mobius

sealed class TaskListEffect {

    data object LoadTasks: TaskListEffect()
    data class PerformDeleteTask(val taskId: Long) : TaskListEffect()

}