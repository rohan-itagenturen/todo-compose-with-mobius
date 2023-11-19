package com.app.mymobiusdemo.ui.create.mobius

sealed class CreateTaskViewEffect {
    data class ShowFeedback(val feedbackType: FeedbackType) : CreateTaskViewEffect()
    data object TaskCreated : CreateTaskViewEffect()

    enum class FeedbackType {
        CreateTaskError
    }
}