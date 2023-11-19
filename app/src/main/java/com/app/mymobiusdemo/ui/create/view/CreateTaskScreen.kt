package com.app.mymobiusdemo.ui.create.view

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.mymobiusdemo.R
import com.app.mymobiusdemo.data.TaskDto
import com.app.mymobiusdemo.ui.create.CreateTaskViewModel
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskEvent
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskModel
import com.app.mymobiusdemo.ui.create.mobius.CreateTaskViewEffect
import com.app.mymobiusdemo.utils.observeWithDisposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    viewModel: CreateTaskViewModel,
    onBackPress: () -> Unit
) {

    val context = LocalContext.current
    val errorCreate = stringResource(R.string.create_error_msg)
    val successCreate = stringResource(R.string.create_success_msg)

    viewModel.viewEffects.observeWithDisposable(onViewEffect = {
        when (it) {
            is CreateTaskViewEffect.ShowFeedback -> {
                val message = when (it.feedbackType) {
                    CreateTaskViewEffect.FeedbackType.CreateTaskError -> errorCreate
                }

                Toast.makeText(context, message, LENGTH_SHORT).show()
            }

            CreateTaskViewEffect.TaskCreated -> {
                Toast.makeText(context, successCreate, LENGTH_SHORT).show()
                onBackPress()
            }
        }
    })

    val model = viewModel.models.observeAsState(CreateTaskModel())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_create_task)) }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            when (model.value.viewState) {
                CreateTaskModel.ViewState.LOADING -> {
                    CircularProgressIndicator()
                }

                CreateTaskModel.ViewState.LOADED -> {
                    LoadedState(onCreate = { title, description ->
                        viewModel.dispatchEvent(
                            CreateTaskEvent.RequestToCreateTask(
                                TaskDto(id = 0, title = title, description = description)
                            )
                        )
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadedState(
    modifier: Modifier = Modifier,
    onCreate: (String, String) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val isEnabled = remember {
        derivedStateOf { title.isNotEmpty() && description.isNotEmpty() }
    }.value


    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(stringResource(R.string.hint_title)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            isError = title.isEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.hint_description)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = false,
            isError = description.isEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .background(MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(10.dp),
            enabled = isEnabled,
            onClick = { onCreate(title, description) }) {
            Text(
                text = stringResource(R.string.button_create),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = modifier.weight(1f, true))
    }

}
