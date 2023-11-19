package com.app.mymobiusdemo.data

import javax.inject.Inject

class TaskDataSource @Inject constructor() {
    private var tasks = mutableListOf(
        TaskDto(1, "Task 1", "Task Description 1"),
        TaskDto(2, "Task 2", "Task Description 2"),
        TaskDto(3, "Task 3", "Task Description 3"),
        TaskDto(4, "Task 4", "Task Description 4"),
        TaskDto(5, "Task 5", "Task Description 5"),
        TaskDto(6, "Task 6", "Task Description 6"),
        TaskDto(7, "Task 7", "Task Description 7"),
        TaskDto(8, "Task 8", "Task Description 8"),
        TaskDto(9, "Task 9", "Task Description 9"),
        TaskDto(10, "Task 10", "Task Description 10"),
    )

    fun getTasks(): List<TaskDto> {
        return tasks
    }

    fun getTaskById(id: Long): TaskDto? {
        return tasks.firstOrNull { it.id == id }
    }

    fun insertTask(task: TaskDto) {
        val newTask = TaskDto(tasks.count() + 1L, task.title, task.description)
        tasks.add(0, newTask)
    }

    fun deleteTaskById(id: Long): Boolean {
        val taskToDelete = tasks.firstOrNull { it.id == id }
        return if (taskToDelete == null) false else {
            tasks.remove(taskToDelete)
            true
        }
    }

    fun updateTask(taskDto: TaskDto): Boolean {
        var isUpdated = false
        tasks.find { it.id == taskDto.id }?.let {
            it.title = taskDto.title
            it.description = taskDto.description

            isUpdated = true
        }

        return isUpdated
    }
}