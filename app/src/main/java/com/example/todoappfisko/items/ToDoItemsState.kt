package com.example.todoappfisko.items

import com.example.todoappfisko.TodoItem

data class ToDoItemsState(val status: Status = Status.Initial, val items: List<TodoItem> = listOf())

enum class Status {
    Initial,
    Loading
}
