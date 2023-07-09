package com.example.todoappfisko

import android.app.Application
import com.example.todoappfisko.di.AppComponent
import com.example.todoappfisko.di.DaggerAppComponent

open class MyApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}
