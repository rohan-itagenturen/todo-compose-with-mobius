package com.app.mymobiusdemo.ui.create.mobius

import com.app.mymobiusdemo.data.TaskDto

sealed class CreateTaskEffect {
    data class PerformCreateTask(val taskDto: TaskDto): CreateTaskEffect()
}