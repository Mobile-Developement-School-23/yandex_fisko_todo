package com.example.todoappfisko.repository.network

import okhttp3.Interceptor
import okhttp3.Response

class BackendAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "OAuth y0_AgAAAAAJxDmZAAocggAAAADmnASUDQlcbbjNTVW7GVv7lbqTLXO2pDY")
            .build()
        return chain.proceed(request)
    }
}