package com.app.mymobiusdemo.ui.taskdetail

import com.app.mymobiusdemo.data.TaskDataSource
import com.app.mymobiusdemo.ui.taskdetail.mobius.TaskDetailEffect
import com.app.mymobiusdemo.ui.taskdetail.mobius.TaskDetailEffectHandler
import com.app.mymobiusdemo.ui.taskdetail.mobius.TaskDetailEvent
import com.app.mymobiusdemo.ui.taskdetail.mobius.TaskDetailModel
import com.app.mymobiusdemo.ui.taskdetail.mobius.TaskDetailModelInit
import com.app.mymobiusdemo.ui.taskdetail.mobius.TaskDetailModelUpdate
import com.app.mymobiusdemo.ui.taskdetail.mobius.TaskDetailViewEffect
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusLoopViewModel
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.functions.Function
import com.spotify.mobius.runners.WorkRunner
import com.spotify.mobius.rx2.RxMobius
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(dataSource: TaskDataSource, workRunner: WorkRunner) :
    MobiusLoopViewModel<TaskDetailModel, TaskDetailEvent, TaskDetailEffect, TaskDetailViewEffect>(
        Function<Consumer<TaskDetailViewEffect>, MobiusLoop.Factory<TaskDetailModel, TaskDetailEvent, TaskDetailEffect>> {
         val sideEffectHandler = TaskDetailEffectHandler(dataSource)
         RxMobius.loop(
             TaskDetailModelUpdate(it),
             sideEffectHandler
         )
        },
        TaskDetailModel(),
        TaskDetailModelInit(),
        workRunner,
        10
    )