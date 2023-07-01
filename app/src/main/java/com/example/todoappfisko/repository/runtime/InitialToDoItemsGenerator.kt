package com.example.todoappfisko.repository.runtime

import com.example.todoappfisko.ToDoItemFactory
import com.example.todoappfisko.ToDoItemImportance
import com.example.todoappfisko.TodoItem


class InitialToDoItemsGenerator {

    private val toDoItemFactory = ToDoItemFactory()

    fun generate(): List<TodoItem> {
        val itemCount = (10..20).random()
        val items = mutableListOf<TodoItem>()
        repeat(itemCount) {
            val item = generateItem()
            items.add(item)
        }
        return items
    }

    private fun generateItem(): TodoItem {
        return toDoItemFactory.create(
            TEXTS.random(),
            ToDoItemImportance.values().random(),
            BOOLEAN_VALUES.random()
        )
    }

    companion object {

        private val BOOLEAN_VALUES = listOf(true, false)
        private val TEXTS = listOf(
            "Купить сыр",
            "Сделать задание для ШМР",
            "Защитить диплом",
            "Длинный длинный текст. Длинный длинный текст. Длинный длинный текст. " +
                    "Длинный длинный текст.Длинный длинный текст. Длинный длинный текст. " +
                    "Длинный длинный текст. Длинный длинный текст.Длинный длинный текст. " +
                    "Длинный длинный текст. Длинный длинный текст. Длинный длинный текст. "
        )
    }
}