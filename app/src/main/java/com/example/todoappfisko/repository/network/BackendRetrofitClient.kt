package com.example.todoappfisko.repository.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object BackendRetrofitClient {

    private val moshi = Moshi.Builder().build()
    private val authInterceptor = BackendAuthInterceptor()
    private val loggingInterceptor = HttpLoggingInterceptor()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    var client: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://beta.mrdekk.ru/todobackend/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}