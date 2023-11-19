package com.app.mymobiusdemo.ui.tasklist.mobius

import com.app.mymobiusdemo.data.TaskDto

data class TaskListModel(val taskLists: List<TaskDto> = emptyList())