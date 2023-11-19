package com.app.mymobiusdemo.ui.taskdetail.mobius

import com.app.mymobiusdemo.data.TaskDto

data class TaskDetailModel(
    val task: TaskDto = TaskDto(0, "", ""),
    val viewState : ViewState = ViewState.LOADING
) {
    enum class ViewState {
        LOADING,
        LOADED
    }
}