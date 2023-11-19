package com.app.mymobiusdemo.ui.create

import com.app.mymobiusdemo.data.TaskDataSource
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskEffect
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskEffectHandler
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskEvent
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskModel
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskModelInit
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskModelUpdate
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskViewEffect
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusLoopViewModel
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.functions.Function
import com.spotify.mobius.runners.WorkRunner
import com.spotify.mobius.rx2.RxMobius
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(dataSource: TaskDataSource, workRunner: WorkRunner) :
    MobiusLoopViewModel<CreateTaskModel, CreateTaskEvent, CreateTaskEffect, CreateTaskViewEffect>(
        Function<Consumer<CreateTaskViewEffect>, MobiusLoop.Factory<CreateTaskModel, CreateTaskEvent, CreateTaskEffect>> {
            val sideEffectHandler = CreateTaskEffectHandler(dataSource)
            RxMobius.loop(
                CreateTaskModelUpdate(it),
                sideEffectHandler
            )
        },
        CreateTaskModel(),
        CreateTaskModelInit(),
        workRunner,
        10
    )