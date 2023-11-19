package com.app.mymobiusdemo.ui

enum class AppScreens {
    Splash,
    TaskDetail,
    CreateTask,
    TaskList;

    companion object {
        fun fromRoute(route: String?): AppScreens {
            return when (route?.substringBefore("/")) {
                Splash.name -> Splash
                TaskDetail.name -> TaskDetail
                CreateTask.name -> CreateTask
                TaskList.name -> TaskList
                null -> Splash
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
        }
    }
}