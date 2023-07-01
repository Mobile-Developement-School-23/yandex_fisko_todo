package com.example.todoappfisko

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoappfisko.items.ToDoItemsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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