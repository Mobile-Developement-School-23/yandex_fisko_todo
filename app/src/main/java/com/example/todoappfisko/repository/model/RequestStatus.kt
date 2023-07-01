package com.example.todoappfisko.repository.model

sealed class RequestStatus

object SuccessRequestStatus : RequestStatus()
class ErrorRequestStatus(val errorMessage: String) : RequestStatus()
