package com.app.mymobiusdemo.ui.create.mobius

import com.spotify.mobius.First
import com.spotify.mobius.Init

object CreateTaskModelInit {

    operator fun invoke() = Init<CreateTaskModel, CreateTaskEffect> {
        First.first(it)
    }
}