package com.app.mymobiusdemo.ui.create.mobius

import com.app.mymobiusdemo.data.TaskDto
import com.spotify.mobius.test.FirstMatchers
import com.spotify.mobius.test.InitSpec
import com.spotify.mobius.test.NextMatchers
import com.spotify.mobius.test.RecordingConsumer
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class CreateTaskModelUpdateTest {

    private val initSpec = InitSpec(CreateTaskModelInit())
    private val testConsumer = RecordingConsumer<CreateTaskViewEffect>()
    private val updateSpec = UpdateSpec(CreateTaskModelUpdate(testConsumer))

    private val defaultModel = CreateTaskModel()


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

    private val newTask = getTestTasks()[0]

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
    fun `Given defaultModel, when event is RequestToCreateTask, then update model with LOADING and trigger PerformCreateTask effect`() {
        updateSpec.given(defaultModel)
            .`when`(CreateTaskEvent.RequestToCreateTask(newTask))
            .then(
                assertThatNext(
                    NextMatchers.hasModel(defaultModel.copy(viewState = CreateTaskModel.ViewState.LOADING)),
                    NextMatchers.hasEffects(CreateTaskEffect.PerformCreateTask(newTask))
                )
            )
    }

    @Test
    fun `When event is OnCreateTaskSuccess, then show TaskCreated with no model change`() {
        updateSpec.given(defaultModel)
            .`when`(CreateTaskEvent.OnCreateTaskSuccess)
            .then {
                testConsumer.assertValues(CreateTaskViewEffect.TaskCreated)
                assert(it.lastNext().hasModel().not())
                assert(it.lastNext().hasEffects().not())
            }
    }
}