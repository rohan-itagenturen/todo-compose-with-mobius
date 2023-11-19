package com.app.mymobiusdemo.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.mymobiusdemo.ui.AppScreens
import com.app.mymobiusdemo.ui.create.CreateTaskViewModel
import com.app.mymobiusdemo.ui.create.view.CreateTaskScreen
import com.app.mymobiusdemo.ui.splash.SplashScreen
import com.app.mymobiusdemo.ui.taskdetail.TaskDetailViewModel
import com.app.mymobiusdemo.ui.taskdetail.view.TaskDetailScreen
import com.app.mymobiusdemo.ui.tasklist.TaskListViewModel
import com.app.mymobiusdemo.ui.tasklist.view.TaskListScreen
import com.app.mymobiusdemo.ui.theme.MyMobiusDemoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMobiusDemoTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = AppScreens.TaskList.name) {
                    composable(AppScreens.Splash.name) {
                        SplashScreen(onNavigateTo = {
                            navController.navigate(it) {
                                //This will remove the SplashScreen from the backStack entry
                                popUpTo(AppScreens.Splash.name) {
                                    inclusive = true
                                }
                            }
                        })
                    }

                    composable(AppScreens.TaskList.name) {
                        val viewModel: TaskListViewModel = hiltViewModel()
                        TaskListScreen(viewModel, onNavigateTo = { navController.navigate(it) })
                    }

                    composable(AppScreens.CreateTask.name) {
                        val viewModel: CreateTaskViewModel = hiltViewModel()
                        CreateTaskScreen(viewModel, onBackPress = { navController.popBackStack() })
                    }

                    composable(
                        "${AppScreens.TaskDetail.name}/{taskId}",
                        arguments = listOf(navArgument("taskId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
                        val viewModel: TaskDetailViewModel = hiltViewModel()
                        TaskDetailScreen(
                            viewModel,
                            taskId,
                            onBackPress = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}