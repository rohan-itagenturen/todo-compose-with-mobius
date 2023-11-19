package com.app.mymobiusdemo.ui.tasklist.mobius

import com.spotify.mobius.First
import com.spotify.mobius.Init

object TaskListModelInit {

    operator fun invoke() = Init<TaskListModel, TaskListEffect> {
        First.first(it, setOf(TaskListEffect.LoadTasks))
    }
}