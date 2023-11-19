package com.app.mymobiusdemo.ui.create.mobius

data class CreateTaskModel(
    val viewState: ViewState = ViewState.LOADED
) {
    enum class ViewState {
        LOADING,
        LOADED
    }
}