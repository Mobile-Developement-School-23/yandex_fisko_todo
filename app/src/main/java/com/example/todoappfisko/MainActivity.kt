package com.example.todoappfisko

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.todoappfisko.R
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.YandexAuthToken
import android.content.SharedPreferences

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var mSettings: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val yandexSdk = YandexAuthSdk(this, YandexAuthOptions(this))
        val loginOptionsBuilder = YandexAuthLoginOptions.Builder()
        yandexSdk.createLoginIntent(loginOptionsBuilder.build())

        val intent = yandexSdk.createLoginIntent(loginOptionsBuilder.build())

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val yandexAuthToken: YandexAuthToken? =
                    yandexSdk.extractToken(it.resultCode, it.data)
                if (yandexAuthToken != null) {
                    Toast.makeText(applicationContext, yandexAuthToken.value, Toast.LENGTH_SHORT)
                        .show()


                    val client = OkHttpClient()

                    val BASE_URL = "https://beta.mrdekk.ru/todobackend"

                    val request = Request.Builder()
                        .url("$BASE_URL/list")
                        .header("Authorization", "OAuth ${yandexAuthToken.value}")
                        .build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            // Обработка ошибки
                            e.printStackTrace()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val responseBody = response.body?.string()

                            // Обработка ответа сервера
                            if (response.isSuccessful) {
                                val jsonResponse = responseBody?.let { JSONObject(it) }
                                val status = jsonResponse?.getString("status")
                                val list = jsonResponse?.getJSONArray("list")
                                val revision = jsonResponse?.getInt("revision")
                                println(
                                    "status ${status}" +
                                            "list ${list}" +
                                            "list ${list}" +
                                            "revision ${revision}"
                                )

                                // Обработка полученных данных
                                // ...
                            } else {
                                // Обработка ошибки сервера
//                val errorBody = response.errorBody?.string()
                                // ...
                            }
                        }
                    })


                }
            }
        }.launch(intent)


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