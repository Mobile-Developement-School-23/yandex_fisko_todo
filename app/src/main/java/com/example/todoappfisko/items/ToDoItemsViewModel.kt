package com.example.todoappfisko.items

import androidx.lifecycle.ViewModel
import com.example.todoappfisko.TodoItem
import com.example.todoappfisko.di.ActivityScope
import com.example.todoappfisko.repository.TodoItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScope
class ToDoItemsViewModel  @Inject constructor (
    private val repository: TodoItemsRepository
) : ViewModel() {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val _state = MutableStateFlow(ToDoItemsState())
    val state: StateFlow<ToDoItemsState> = _state.asStateFlow()

    init {
        scope.launch {
            repository.todoItems.collect { items ->
                _state.update { state ->
                    state.copy(items = items)
                }
            }
        }
    }

    fun removeItem(item: TodoItem) {
        scope.launch {
            repository.removeItem(item)
        }
    }
}
