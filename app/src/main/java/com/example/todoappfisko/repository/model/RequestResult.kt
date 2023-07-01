package com.example.todoappfisko.repository.model

sealed class RequestResult<T>

class SuccessRequestResult<T>(val value: T) : RequestResult<T>()
class ErrorRequestResult<T>(val errorMessage: String) : RequestResult<T>()