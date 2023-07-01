package com.example.todoappfisko.repository.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BackendService {

    @Headers("Content-Type: application/json")
    @GET("list")
    suspend fun getTodoItems(): Response<BackendList>

    @Headers("Content-Type: application/json")
    @GET("list/{item_id}")
    suspend fun getItem(
        @Path("item_id") itemId: String
    ): Response<BackendElement>

    @Headers("Content-Type: application/json")
    @POST("list")
    suspend fun addItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body body: BackendElement,
    ): Response<BackendElement>

    @Headers("Content-Type: application/json")
    @PUT("list/{item_id}")
    suspend fun editItem(
        @Path("item_id") itemId: String,
        @Body body: BackendElement,
    ): Response<BackendElement>

    @Headers("Content-Type: application/json")
    @DELETE("list/{item_id}")
    suspend fun removeItem(
        @Path("item_id") itemId: String
    ): Response<ResponseBody>
}
