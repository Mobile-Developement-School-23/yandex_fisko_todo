package com.example.todoappfisko

import android.app.Activity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


//data class SignInterceptor(
//    val email: String,
//    val password: String
//)
//data class SignInResponseBody(
//    val token :String
//)
//
//val contentType = "application/json; charset=utf-8".toMediaType()
//fun main() {
//    val sdk = YandexAuthSdk(
//        requireContext(), Builder(requireContext())
//            .enableLogging() // Only in testing builds
//            .build()
//    )
//
//}
//
//fun makeOkHttpClient(): OkHttpClient {
//    return OkHttpClient.Builder()
//        .addInterceptor(makeLoggingInterceptor())
//        .connectTimeout(120, TimeUnit.SECONDS)
//        .readTimeout(120, TimeUnit.SECONDS)
//        .writeTimeout(90, TimeUnit.SECONDS)
//        .build()
//}
//
//fun makeLoggingInterceptor(): HttpLoggingInterceptor {
//    val logging = HttpLoggingInterceptor()
//    logging.level = HttpLoggingInterceptor.Level.BODY
//    return logging
//}