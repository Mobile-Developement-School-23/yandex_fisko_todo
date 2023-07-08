package com.example.todoappfisko

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoappfisko.di.RegistrationComponent
import com.example.todoappfisko.items.ToDoItemsFragment

class MainActivity : AppCompatActivity() {

    lateinit var registrationComponent: RegistrationComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationComponent = (application as MyApplication).appComponent
            .registrationComponent().create()

        // Injects this activity to the just created Registration component
        registrationComponent.inject(this)

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ToDoItemsFragment())
                .addToBackStack("")
                .commit()
        }
    }



    companion object {
        const val APP_TOKEN_YANDEX: String = "Token Yandex"
    }
}