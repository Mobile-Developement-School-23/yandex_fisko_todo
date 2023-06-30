package com.fisko.yandex.todoapp

import com.example.todoappfisko.TodoItem

object TodoItemsRepository {

    private val listeners = mutableListOf<OnItemsChangeListener>()
    private val toDoItemsGenerator: InitialToDoItemsGenerator = InitialToDoItemsGenerator()
    private val mutableToDoItems = toDoItemsGenerator.generate().toMutableList()

    val todoItems: List<TodoItem>
        get() {
            return mutableToDoItems
        }

    fun addItemsChangeListener(listener: OnItemsChangeListener) {
        listeners.add(listener)
    }

    fun removeItemsChangeListener(listener: OnItemsChangeListener) {
        listeners.remove(listener)
    }

    fun getItem(itemId: String): TodoItem? {
        return mutableToDoItems.firstOrNull { it.id == itemId }
    }

    fun addItem(item: TodoItem) {
        val itemPosition = mutableToDoItems.indexOfFirst { it.id == item.id }
        if (itemPosition >= 0) {
            mutableToDoItems[itemPosition] = item
        } else {
            mutableToDoItems.add(item)
        }
        notifyItemChange()
    }

    fun removeItem(item: TodoItem) {
        mutableToDoItems.remove(item)
        notifyItemChange()
    }

    private fun notifyItemChange() {
        listeners.forEach { it.onItemsChange(mutableToDoItems) }
    }

    interface OnItemsChangeListener {
        fun onItemsChange(items: List<TodoItem>)
    }
}