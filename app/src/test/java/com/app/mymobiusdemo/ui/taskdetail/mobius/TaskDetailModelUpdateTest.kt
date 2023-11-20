package com.app.mymobiusdemo.ui.taskdetail.mobius

import com.app.mymobiusdemo.data.TaskDto
import com.spotify.mobius.test.FirstMatchers
import com.spotify.mobius.test.InitSpec
import com.spotify.mobius.test.NextMatchers
import com.spotify.mobius.test.RecordingConsumer
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class TaskDetailModelUpdateTest {

    private val initSpec = InitSpec(TaskDetailModelInit())
    private val testConsumer = RecordingConsumer<TaskDetailViewEffect>()
    private val updateSpec = UpdateSpec(TaskDetailModelUpdate(testConsumer))

    private val defaultModel = TaskDetailModel()


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

    private val selectedTask = getTestTasks()[0]

    @Test
    fun `when loop initiates then it has default model and no effects`() {
        initSpec.whenInit(defaultModel).then(
            InitSpec.assertThatFirst(
                FirstMatchers.hasModel(defaultModel),
                FirstMatchers.hasNoEffects()
            )
        )
    }

    @Test
    fun `Given defaultModel, when event is RequestTaskLoad, then update model with LOADING and trigger PerformLoadTask effect`() {
        updateSpec.given(defaultModel)
            .`when`(TaskDetailEvent.RequestTaskLoad(selectedTask.id))
            .then(
                assertThatNext(
                    NextMatchers.hasModel(defaultModel.copy(viewState = TaskDetailModel.ViewState.LOADING)),
                    NextMatchers.hasEffects(TaskDetailEffect.PerformLoadTask(selectedTask.id))
                )
            )
    }

    @Test
    fun `When event is OnTaskLoadSuccess, then show ShowTaskDetail with no model change`() {
        updateSpec.given(defaultModel)
            .`when`(TaskDetailEvent.OnTaskLoadSuccess(selectedTask))
            .then(
                assertThatNext(
                    NextMatchers.hasModel(
                        defaultModel.copy(
                            task = selectedTask,
                            viewState = TaskDetailModel.ViewState.LOADED
                        )
                    ),
                    NextMatchers.hasNoEffects()
                )
            )
    }

    @Test
    fun `When event is OnTaskLoadError, then show Feedback with no model change`() {
        updateSpec.given(defaultModel)
            .`when`(TaskDetailEvent.OnTaskLoadError)
            .then {
                testConsumer.assertValues(TaskDetailViewEffect.ShowFeedback(TaskDetailViewEffect.FeedbackType.TaskLoadError))
                assert(it.lastNext().hasModel().not())
                assert(it.lastNext().hasEffects().not())
            }
    }

    @Test
    fun `When event is RequestUpdateTask, then dispatch PerformUpdateTask effect with LOADING state`() {
        val selectedTask = getTestTasks()[0]
        updateSpec.given(defaultModel)
            .`when`(TaskDetailEvent.RequestUpdateTask(selectedTask))
            .then(
                assertThatNext(
                    NextMatchers.hasModel(defaultModel.copy(viewState = TaskDetailModel.ViewState.LOADING)),
                    NextMatchers.hasEffects(TaskDetailEffect.PerformUpdateTask(selectedTask))
                )
            )
    }

    @Test
    fun `When event is OnTaskUpdateError, then show Feedback error with LOADED state`() {
        updateSpec.given(defaultModel)
            .`when`(TaskDetailEvent.OnTaskUpdateError)
            .then {
                testConsumer.assertValues(TaskDetailViewEffect.ShowFeedback(TaskDetailViewEffect.FeedbackType.UpdateTaskError))
                assert(it.lastNext().modelUnsafe().viewState == TaskDetailModel.ViewState.LOADED)
            }
    }

    @Test
    fun `When event is OnTaskUpdateSuccess, then show TaskUpdate with no model and effect`() {
        updateSpec.given(defaultModel)
            .`when`(TaskDetailEvent.OnTaskUpdateSuccess)
            .then {
                testConsumer.assertValues(TaskDetailViewEffect.TaskUpdated)
                assert(it.lastNext().hasModel().not())
                assert(it.lastNext().hasEffects().not())
            }
    }
}