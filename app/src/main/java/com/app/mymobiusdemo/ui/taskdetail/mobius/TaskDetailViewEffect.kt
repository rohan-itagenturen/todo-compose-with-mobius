package com.app.mymobiusdemo.ui.taskdetail.mobius

sealed class TaskDetailViewEffect {
    data class ShowFeedback(val feedbackType: FeedbackType) : TaskDetailViewEffect()
    data object TaskUpdated : TaskDetailViewEffect()

    enum class FeedbackType {
        UpdateTaskError,
        TaskLoadError
    }
}