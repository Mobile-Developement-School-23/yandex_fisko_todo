package com.example.todoappfisko

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: ToDoItemsImportance,
    val deadlineDate: Date,
    val completed: Boolean,
    val creationDate: Date,
    val editDate: Date,
)

//cups
enum class ToDoItemsImportance {
    Low,
    Normal,
    Urgent,
}