package com.app.mymobiusdemo.ui.taskdetail.mobius

import com.app.mymobiusdemo.data.TaskDto

sealed class TaskDetailEffect {
    data class PerformLoadTask(val taskId: Long): TaskDetailEffect()
    data class PerformUpdateTask(val task: TaskDto) : TaskDetailEffect()
}