package com.example.todolist.model

import java.io.Serializable

data class Task(
    val id: Long = 0L,
    val title: String,
    val description: String = "",
    val category: String = "General",
    val priority: String = "Medium", // High, Medium, Low
    val dueDate: String = "Today",
    val isCompleted: Boolean = false,
    val createdTimestamp: Long = System.currentTimeMillis(),
    val completedTimestamp: Long? = null,
    val reminderTimestamp: Long? = null
) : Serializable