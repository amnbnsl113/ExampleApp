package com.example.exampleapp.model

data class StateModel<T>(
    val data: T?,
    val isLoading: Boolean,
    val isError: Boolean,
    val errorMessage: String?
)
