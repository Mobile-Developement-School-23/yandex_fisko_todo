package com.example.todoappfisko.repository

import com.example.todoappfisko.TodoItem
import com.example.todoappfisko.repository.model.RequestResult
import com.example.todoappfisko.repository.model.RequestStatus
import com.example.todoappfisko.repository.model.SuccessRequestResult
import com.example.todoappfisko.repository.network.NetworkTodoItemsDataSource
import com.example.todoappfisko.repository.runtime.RuntimeTodoItemsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoItemsRepository @Inject constructor(
    private val networkTodoItemsDataSource: NetworkTodoItemsDataSource,
    private val runtimeTodoItemsDataSource: RuntimeTodoItemsDataSource
) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val _itemsUpdateFlow = MutableSharedFlow<Unit>(replay = 0)

    init {
        scope.launch { updateItems() }
        scope.launch {
            _itemsUpdateFlow.collect { updateItems() }
        }
    }


    private val _todoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItems: Flow<List<TodoItem>> = _todoItems

    private suspend fun updateItems() {
        val items = getTodoItems()
        _todoItems.update { items }
    }

    private suspend fun getTodoItems(): List<TodoItem> {
        val networkResult = networkTodoItemsDataSource.getTodoItems()
        if (networkResult is SuccessRequestResult<List<TodoItem>>) {
            runtimeTodoItemsDataSource.updateItems(networkResult.value)
        }

        val itemsResult = runtimeTodoItemsDataSource.getTodoItems()
        val items = if (itemsResult is SuccessRequestResult) {
            itemsResult.value
        } else {
            emptyList()
        }

        return items
    }

    suspend fun getItem(itemId: String): RequestResult<TodoItem> {
        val networkResult = networkTodoItemsDataSource.getItem(itemId)
        if (networkResult is SuccessRequestResult<TodoItem>) {
            runtimeTodoItemsDataSource.addItem(networkResult.value)
        }

        return runtimeTodoItemsDataSource.getItem(itemId)
    }

    suspend fun addItem(item: TodoItem): RequestStatus {
        networkTodoItemsDataSource.addItem(item)
        _itemsUpdateFlow.emit(Unit)
        return runtimeTodoItemsDataSource.addItem(item)
    }

    suspend fun removeItem(item: TodoItem): RequestStatus {
        networkTodoItemsDataSource.removeItem(item)
        _itemsUpdateFlow.emit(Unit)
        return runtimeTodoItemsDataSource.removeItem(item)
    }
}