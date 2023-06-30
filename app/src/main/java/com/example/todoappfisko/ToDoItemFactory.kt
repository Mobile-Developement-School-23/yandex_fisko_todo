package com.example.todoappfisko

import com.example.todoappfisko.ToDoItemsImportance
import com.example.todoappfisko.TodoItem
import java.util.Calendar
import java.util.Date
import java.util.UUID

class ToDoItemFactory(){
    fun create (text:String, importance: ToDoItemsImportance, completed:Boolean): TodoItem {
       return TodoItem(
            id = getRandomString(),
            text=text,
            importance=importance,
            deadlineDate = getCurrentDateTime(),
            completed=completed,
            creationDate = getCurrentDateTime(),
            editDate = getCurrentDateTime()
        )
    }
    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
    private fun getRandomString(): String {
        return UUID.randomUUID().toString()
    }
}