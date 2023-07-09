package com.example.todoappfisko.di

import com.example.todoappfisko.MainActivity
import com.example.todoappfisko.ToDoItemsAdapter
import com.example.todoappfisko.item.ItemViewerViewModel
import com.example.todoappfisko.items.ToDoItemsFragment
import com.example.todoappfisko.items.ToDoItemsViewModel
import com.example.todoappfisko.repository.TodoItemsRepository
import dagger.Module

// This module tells a Component which are its subcomponents
@Module
class AppSubcomponents
