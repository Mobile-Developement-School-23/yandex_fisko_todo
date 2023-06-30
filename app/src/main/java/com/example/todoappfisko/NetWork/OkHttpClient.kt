package com.example.todoappfisko.NetWork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappfisko.MainActivity
import com.example.todoappfisko.MainActivity.Companion.APP_TOKEN_YANDEX

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import kotlin.concurrent.thread

//
////fun main() {
//////    LoginViewModel().login()
////
////    thread {
////        val sharedPreferencestoken = MainActivity().mSettings.getString(APP_TOKEN_YANDEX, token)
////    }.start()
////}
//class LoginViewModel(): ViewModel() {
//
//    fun login() {
//        // Create a new coroutine to move the execution off the UI thread
//        viewModelScope.launch(Dispatchers.IO) {
//            val sharedPreferencestoken = MainActivity().mSettings.getString(APP_TOKEN_YANDEX, token)
//            getListFromServer(sharedPreferencestoken)
//        }
//    }}
//
//
//
//val client = OkHttpClient()
//
//const val BASE_URL = "https://beta.mrdekk.ru/todobackend"
//val token= ""
//
//
//
//fun getListFromServer(sharedPreferencestoken: String?) {
//    val request = Request.Builder()
//        .url("$BASE_URL/list")
//        .header("Authorization", "OAuth $sharedPreferencestoken" )
//        .build()
//
//    client.newCall(request).enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            // Обработка ошибки
//            e.printStackTrace()
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            val responseBody = response.body?.string()
//
//            // Обработка ответа сервера
//            if (response.isSuccessful) {
//                val jsonResponse = responseBody?.let { JSONObject(it) }
//                val status = jsonResponse?.getString("status")
//                val list = jsonResponse?.getJSONArray("list")
//                val revision = jsonResponse?.getInt("revision")
//                println(
//                    "status ${status}" +
//                            "list ${list}" +
//                            "list ${list}" +
//                            "revision ${revision}"
//                )
//
//                // Обработка полученных данных
//                // ...
//            } else {
//                // Обработка ошибки сервера
////                val errorBody = response.errorBody?.string()
//                // ...
//            }
//        }
//    })
//}