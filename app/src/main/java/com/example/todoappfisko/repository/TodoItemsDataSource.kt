package com.example.todoappfisko.repository

import com.example.todoappfisko.TodoItem
import com.example.todoappfisko.repository.model.RequestResult
import com.example.todoappfisko.repository.model.RequestStatus

interface TodoItemsDataSource {

    suspend fun getTodoItems(): RequestResult<List<TodoItem>>

    suspend fun getItem(itemId: String): RequestResult<TodoItem>

    suspend fun addItem(item: TodoItem): RequestStatus

    suspend fun removeItem(item: TodoItem): RequestStatus
}