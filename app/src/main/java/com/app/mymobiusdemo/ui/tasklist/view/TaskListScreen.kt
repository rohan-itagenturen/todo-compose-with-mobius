package com.app.mymobiusdemo.ui.tasklist.view


import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.app.mymobiusdemo.R
import com.app.mymobiusdemo.data.TaskDto
import com.app.mymobiusdemo.utils.observeWithDisposable
import com.app.mymobiusdemo.ui.AppScreens
import com.app.mymobiusdemo.ui.tasklist.TaskListViewModel
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListEvent
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListModel
import com.app.mymobiusdemo.ui.tasklist.mobius.TaskListViewEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    onNavigateTo: (String) -> Unit
) {
    val context = LocalContext.current
    val deleteErrorMsg = stringResource(R.string.delete_error_msg)
    val deleteSuccessMsg = stringResource(R.string.delete_success_msg)

    val taskListModel = viewModel.models.observeAsState(TaskListModel())

    viewModel.viewEffects.observeWithDisposable {
        when (it) {
            is TaskListViewEffect.ShowTaskDetails -> onNavigateTo("${AppScreens.TaskDetail.name}/${it.taskId}")
            is TaskListViewEffect.ShowCreateTask -> onNavigateTo(AppScreens.CreateTask.name)
            is TaskListViewEffect.ShowFeedback -> {
                val feedbackMessage = when (it.feedbackType) {
                    TaskListViewEffect.FeedbackType.DeleteTaskError -> deleteErrorMsg
                    TaskListViewEffect.FeedbackType.DeleteTaskSuccess -> deleteSuccessMsg
                }
                Toast.makeText(context, feedbackMessage, LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.title_task_list)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.dispatchEvent(TaskListEvent.OnCreateTask) }) {
                Icon(Icons.Filled.Add, stringResource(R.string.content_desc_create_new_task))
            }
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            MyTasksView(
                taskListModel.value.taskLists,
                onTaskClick = { taskId ->
                    viewModel.dispatchEvent(TaskListEvent.OnTaskSelected(taskId))
                },
                onTaskDelete = { taskId ->
                    viewModel.dispatchEvent(TaskListEvent.OnTaskDelete(taskId))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTasksView(tasks: List<TaskDto>, onTaskClick: (Long) -> Unit, onTaskDelete: (Long) -> Unit) {
    LazyColumn {
        items(tasks.size) { index ->
            val taskDto = tasks[index]
            ListItem(
                modifier = Modifier.clickable { onTaskClick(taskDto.id) },
                headlineText = { Text(text = taskDto.title) },
                supportingText = { Text(text = taskDto.description) },
                trailingContent = {
                    IconButton(
                        onClick = { onTaskDelete(taskDto.id) }
                    ) {
                        Icon(Icons.Filled.Delete, stringResource(R.string.content_desc_delete))
                    }
                }
            )
        }
    }
}