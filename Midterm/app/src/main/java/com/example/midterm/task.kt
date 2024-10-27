package com.example.midterm

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var isCompleted: Boolean = false
)
