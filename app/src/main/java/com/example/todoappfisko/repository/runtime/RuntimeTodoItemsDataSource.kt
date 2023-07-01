package com.example.todoappfisko.repository.runtime

import com.example.todoappfisko.TodoItem
import com.example.todoappfisko.repository.TodoItemsDataSource
import com.example.todoappfisko.repository.model.ErrorRequestResult
import com.example.todoappfisko.repository.model.ErrorRequestStatus
import com.example.todoappfisko.repository.model.RequestResult
import com.example.todoappfisko.repository.model.RequestStatus
import com.example.todoappfisko.repository.model.SuccessRequestResult
import com.example.todoappfisko.repository.model.SuccessRequestStatus

class RuntimeTodoItemsDataSource : TodoItemsDataSource {

    private val toDoItemsGenerator: InitialToDoItemsGenerator = InitialToDoItemsGenerator()
    private val mutableToDoItems = toDoItemsGenerator.generate().toMutableList()

    override suspend fun getTodoItems(): RequestResult<List<TodoItem>> {
        return SuccessRequestResult(mutableToDoItems)
    }

    fun updateItems(items: List<TodoItem>) {
        mutableToDoItems.clear()
        mutableToDoItems.addAll(items)
    }

    override suspend fun getItem(itemId: String): RequestResult<TodoItem> {
        val item = mutableToDoItems.firstOrNull { it.id == itemId }
        return if (item != null) {
            SuccessRequestResult(item)
        } else {
            ErrorRequestResult(NOT_FOUND)
        }
    }

    override suspend fun addItem(item: TodoItem): RequestStatus {
        val itemPosition = mutableToDoItems.indexOfFirst { it.id == item.id }
        if (itemPosition >= 0) {
            mutableToDoItems[itemPosition] = item
        } else {
            mutableToDoItems.add(item)
        }

        return SuccessRequestStatus
    }

    override suspend fun removeItem(item: TodoItem): RequestStatus {
        val result = mutableToDoItems.remove(item)
        return if (result) {
            SuccessRequestStatus
        } else {
            ErrorRequestStatus(NETWORK_ERROR)
        }
    }

    private companion object {
        private const val NETWORK_ERROR = "Network error"
        private const val NOT_FOUND = "Not found"
    }
}