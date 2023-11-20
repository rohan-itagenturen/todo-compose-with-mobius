package com.app.mymobiusdemo.ui.tasklist.mobius

import com.app.mymobiusdemo.data.TaskDto
import com.spotify.mobius.test.FirstMatchers
import com.spotify.mobius.test.InitSpec
import com.spotify.mobius.test.NextMatchers
import com.spotify.mobius.test.RecordingConsumer
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class TaskListModelUpdateTest {

    private val initSpec = InitSpec(TaskListModelInit())
    private val testConsumer = RecordingConsumer<TaskListViewEffect>()
    private val updateSpec = UpdateSpec(TaskListModelUpdate(testConsumer))

    private val defaultModel = TaskListModel()

    private fun getTestTasks() = mutableListOf(
        TaskDto(1, "Task 1", "Task Description 1"),
        TaskDto(2, "Task 2", "Task Description 2"),
        TaskDto(3, "Task 3", "Task Description 3"),
        TaskDto(4, "Task 4", "Task Description 4"),
        TaskDto(5, "Task 5", "Task Description 5"),
        TaskDto(6, "Task 6", "Task Description 6"),
        TaskDto(7, "Task 7", "Task Description 7"),
        TaskDto(8, "Task 8", "Task Description 8"),
        TaskDto(9, "Task 9", "Task Description 9"),
        TaskDto(10, "Task 10", "Task Description 10")
    )

    @Test
    fun `when loop initiates then it has default model and LoadTasks as effect`() {
        initSpec.whenInit(defaultModel).then(
            InitSpec.assertThatFirst(
                FirstMatchers.hasModel(defaultModel),
                FirstMatchers.hasEffects(TaskListEffect.LoadTasks)
            )
        )
    }

    @Test
    fun `Given defaultModel, when event is OnTaskLoaded, then update model with new tasks`() {
        val loadedTask = getTestTasks()
        updateSpec.given(defaultModel)
            .`when`(TaskListEvent.OnTaskLoaded(loadedTask))
            .then(
                assertThatNext(
                    NextMatchers.hasModel(defaultModel.copy(taskLists = loadedTask))
                )
            )
    }

    @Test
    fun `When event is OnTaskSelected, then show ShowTaskDetail with no model change`() {
        val selectedTask = getTestTasks()[0]
        updateSpec.given(defaultModel)
            .`when`(TaskListEvent.OnTaskSelected(selectedTask.id))
            .then {
                testConsumer.assertValues(TaskListViewEffect.ShowTaskDetails(selectedTask.id))
                assert(it.lastNext().hasModel().not())
                assert(it.lastNext().hasEffects().not())
            }
    }

    @Test
    fun `When event is OnCreateTask, then show ShowCreateTask with no model change`() {
        updateSpec.given(defaultModel)
            .`when`(TaskListEvent.OnCreateTask)
            .then {
                testConsumer.assertValues(TaskListViewEffect.ShowCreateTask)
                assert(it.lastNext().hasModel().not())
                assert(it.lastNext().hasEffects().not())
            }
    }

    @Test
    fun `When event is OnTaskDelete, then dispatch PerformDeleteTask effect`() {
        val selectedTask = getTestTasks()[0]
        updateSpec.given(defaultModel)
            .`when`(TaskListEvent.OnTaskDelete(selectedTask.id))
            .then(
                assertThatNext(
                    NextMatchers.hasEffects(TaskListEffect.PerformDeleteTask(selectedTask.id))
                )
            )
    }

    @Test
    fun `When event is OnTaskDeleteSuccess, then show Feedback deleteTask`() {
        updateSpec.given(defaultModel)
            .`when`(TaskListEvent.OnTaskDeleteSuccess)
            .then {
                testConsumer.assertValues(TaskListViewEffect.ShowFeedback(TaskListViewEffect.FeedbackType.DeleteTaskSuccess))
                assert(it.lastNext().hasModel().not())
                assert(it.lastNext().effects().contains(TaskListEffect.LoadTasks))
            }
    }

    @Test
    fun `When event is OnTaskDeleteError, then show Feedback deleteTask error`() {
        updateSpec.given(defaultModel)
            .`when`(TaskListEvent.OnTaskDeleteError)
            .then {
                testConsumer.assertValues(TaskListViewEffect.ShowFeedback(TaskListViewEffect.FeedbackType.DeleteTaskError))
                assert(it.lastNext().hasModel().not())
                assert(it.lastNext().hasEffects().not())
            }
    }
}