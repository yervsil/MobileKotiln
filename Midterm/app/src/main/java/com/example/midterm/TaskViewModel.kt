package com.example.midterm



import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel : ViewModel() {

    private val _taskList = MutableStateFlow(
        listOf(
            Task(1, "Finish report", "Finish midterm projects report"),
            Task(2, "Complete 2 weekly sprint", "Mark all tasks as completed")
        )
    )
    val taskList: StateFlow<List<Task>> = _taskList

    fun addTask(title: String, description: String) {
        val newTask = Task(
            id = (_taskList.value.maxOfOrNull { it.id } ?: 0) + 1,
            title = title,
            description = description
        )
        _taskList.value = _taskList.value + newTask
    }

    fun updateTask(task: Task) {
        _taskList.value = _taskList.value.map {
            if (it.id == task.id) task else it
        }
    }

    fun deleteTask(taskId: Int) {
        _taskList.value = _taskList.value.filter { it.id != taskId }
    }

    fun getTaskById(taskId: Int): Task? {
        return _taskList.value.find { it.id == taskId }
    }
}

