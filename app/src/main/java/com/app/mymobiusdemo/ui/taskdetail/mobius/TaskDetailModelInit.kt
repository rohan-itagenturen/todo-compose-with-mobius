package com.app.mymobiusdemo.ui.taskdetail.mobius

import com.spotify.mobius.First
import com.spotify.mobius.Init

object TaskDetailModelInit {

    operator fun invoke() = Init<TaskDetailModel, TaskDetailEffect> {
        First.first(it)
    }
}