package com.example.todoappfisko.di

import com.example.todoappfisko.MainActivity
import com.example.todoappfisko.item.ItemViewerFragment
import com.example.todoappfisko.items.ToDoItemsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface RegistrationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationComponent
    }


    fun inject(activity: MainActivity)
    fun inject(fragment: ItemViewerFragment)
    fun inject(fragment: ToDoItemsFragment)
}
