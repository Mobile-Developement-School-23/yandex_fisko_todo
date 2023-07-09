package com.example.todoappfisko

import android.view.ViewDebug.IntToString
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class ToDoItemFactory @Inject constructor(){
    fun create(text: String, importance: ToDoItemImportance, completed: Boolean): TodoItem {
        return TodoItem(
            id = getRandomId(),
            text = text,
            importance = importance,
            deadlineDate = getCurrentDateTime(),
            completed = completed,
            createdAt = getCurrentDateTime(),
            changedAt = getCurrentDateTime(),
            lastUpdatedBy = getRandomId()
        )
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun getRandomId(): String {
        return UUID.randomUUID().toString()
    }
}