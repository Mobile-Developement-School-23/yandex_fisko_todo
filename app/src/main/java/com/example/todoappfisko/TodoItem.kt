package com.example.todoappfisko

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: ToDoItemImportance,
    val completed: Boolean,
    val deadlineDate: Date,
    val createdAt: Date,
    val changedAt: Date,
    val lastUpdatedBy: String
)

//cups
enum class ToDoItemImportance {
    Low,
    Normal,
    Urgent,
}