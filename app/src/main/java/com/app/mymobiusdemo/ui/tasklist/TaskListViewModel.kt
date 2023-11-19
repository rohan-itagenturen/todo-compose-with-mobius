package com.app.mymobiusdemo.ui.tasklist

import com.app.mymobiusdemo.data.TaskDataSource
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListEffect
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListEffectHandler
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListEvent
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListModel
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListModelInit
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListModelUpdate
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListViewEffect
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusLoopViewModel
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.functions.Function
import com.spotify.mobius.runners.WorkRunner
import com.spotify.mobius.rx2.RxMobius
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(dataSource: TaskDataSource, workRunner: WorkRunner) :
    MobiusLoopViewModel<TaskListModel, TaskListEvent, TaskListEffect, TaskListViewEffect>(
        Function<Consumer<TaskListViewEffect>, MobiusLoop.Factory<TaskListModel, TaskListEvent, TaskListEffect>> {
         val sideEffectHandler = TaskListEffectHandler(dataSource)
         RxMobius.loop(
             TaskListModelUpdate(it),
             sideEffectHandler
         )
        },
        TaskListModel(),
        TaskListModelInit(),
        workRunner,
        10
    )