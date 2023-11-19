package com.app.mymobiusdemo.ui.tasklist.mobius

sealed class TaskListViewEffect {
    data class ShowTaskDetails(val taskId: Long) : TaskListViewEffect()
    data object ShowCreateTask : TaskListViewEffect()
    data class ShowFeedback(val feedbackType: FeedbackType) : TaskListViewEffect()

    enum class FeedbackType {
        DeleteTaskError,
        DeleteTaskSuccess
    }
}