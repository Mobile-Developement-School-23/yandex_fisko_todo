package com.example.todoappfisko.item

import androidx.lifecycle.ViewModel
import com.example.todoappfisko.ToDoItemFactory
import com.example.todoappfisko.ToDoItemImportance
import com.example.todoappfisko.repository.TodoItemsRepository
import com.example.todoappfisko.repository.model.SuccessRequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class ItemViewerViewModel(
    private val repository: TodoItemsRepository = TodoItemsRepository.getInstance(),
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val toDoItemFactory: ToDoItemFactory = ToDoItemFactory()
) : ViewModel() {

    private val _state = MutableStateFlow(ToDoItemState())
    val state: StateFlow<ToDoItemState> = _state.asStateFlow()

    fun initialize(itemId: String) {
        scope.launch {
            _state.update { state ->
                state.copy(status = Status.Loading)
            }

            val itemResult = repository.getItem(itemId)
            val item = if (itemResult is SuccessRequestResult) itemResult.value else null

            _state.update { state ->
                state.copy(status = Status.Initial, item = item)
            }
        }
    }

    fun addItem(newText: String, newImportance: ToDoItemImportance) {
        scope.launch {
            val item = _state.value.item
            val updatedItem = item?.copy(text = newText, importance = newImportance)
                ?: toDoItemFactory.create(newText, newImportance, false)

            repository.addItem(updatedItem)
        }
    }
}
