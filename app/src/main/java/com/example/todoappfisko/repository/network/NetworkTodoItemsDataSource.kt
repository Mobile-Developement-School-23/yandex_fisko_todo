package com.example.todoappfisko.repository.network

import com.example.todoappfisko.ToDoItemImportance
import com.example.todoappfisko.TodoItem
import com.example.todoappfisko.repository.TodoItemsDataSource
import com.example.todoappfisko.repository.model.ErrorRequestResult
import com.example.todoappfisko.repository.model.ErrorRequestStatus
import com.example.todoappfisko.repository.model.RequestResult
import com.example.todoappfisko.repository.model.RequestStatus
import com.example.todoappfisko.repository.model.SuccessRequestResult
import com.example.todoappfisko.repository.model.SuccessRequestStatus
import java.util.Date

class NetworkTodoItemsDataSource(
    private val service: BackendService = BackendRetrofitClient.client.create(BackendService::class.java)
) : TodoItemsDataSource {

    private var revision: Int = 0

    override suspend fun getTodoItems(): RequestResult<List<TodoItem>> {
        val itemsResponse = service.getTodoItems()
        val items = itemsResponse.body()
        return if (items != null) {
            revision = items.revision
            SuccessRequestResult(items.list.map { it.toLocalItem() })
        } else {
            ErrorRequestResult(NETWORK_ERROR)
        }
    }

    override suspend fun getItem(itemId: String): RequestResult<TodoItem> {
        val itemResponse = service.getItem(itemId)
        val item = itemResponse.body()
        return if (item != null) {
            revision = item.revision
            SuccessRequestResult(item.element.toLocalItem())
        } else {
            ErrorRequestResult(NETWORK_ERROR)
        }
    }

    override suspend fun addItem(item: TodoItem): RequestStatus {
        val element = BackendElement(STATUS_OK, item.toLocalItem(), revision)
        val editResultResponse = service.editItem(item.id, element)
        val editResult = editResultResponse.body()
        return if (editResult != null) {
            SuccessRequestStatus
        } else {
            val addResultResponse = service.addItem(revision, element)
            val addResult = addResultResponse.body()
            if (addResult != null) {
                SuccessRequestStatus
            } else {
                ErrorRequestStatus(NETWORK_ERROR)
            }
        }
    }

    override suspend fun removeItem(item: TodoItem): RequestStatus {
        val removeResultResponse = service.removeItem(item.id)
        val removeResult = removeResultResponse.body()
        return if (removeResult != null) {
            SuccessRequestStatus
        } else {
            ErrorRequestStatus(NETWORK_ERROR)
        }
    }

    private fun BackendItem.toLocalItem(): TodoItem {
        return TodoItem(
            id, text, importance.toLocalImportance(), completed,
            deadlineDate = Date(deadline), createdAt = Date(createdAt),
            changedAt = Date(changedAt), lastUpdatedBy = lastUpdatedBy
        )
    }

    private fun BackendImportance.toLocalImportance(): ToDoItemImportance {
        return when (this) {
            BackendImportance.LOW -> ToDoItemImportance.Low
            BackendImportance.BASIC -> ToDoItemImportance.Normal
        }
    }

    private fun TodoItem.toLocalItem(): BackendItem {
        return BackendItem(
            id, text, importance.toLocalImportance(), completed,
            deadline = deadlineDate.time, createdAt = createdAt.time,
            changedAt = changedAt.time, lastUpdatedBy = lastUpdatedBy
        )
    }

    private fun ToDoItemImportance.toLocalImportance(): BackendImportance {
        return when (this) {
            ToDoItemImportance.Low -> BackendImportance.LOW
            ToDoItemImportance.Normal -> BackendImportance.BASIC
            ToDoItemImportance.Urgent -> BackendImportance.BASIC
        }
    }

    private companion object {
        private const val STATUS_OK = "ok"
        private const val NETWORK_ERROR = "network_error"
    }
}