package com.example.todoappfisko.item

import com.example.todoappfisko.TodoItem

data class ToDoItemState(val status: Status = Status.Initial, val item: TodoItem? = null)

enum class Status {
    Initial,
    Loading
}
