package com.example.exampleapp.model

import com.example.exampleapp.model.Error.Companion.NO_ERROR
import kotlin.Error

data class StateModel<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: Error = NO_ERROR,
)

data class Error(
    val isError: Boolean,
    val errorMessage: String?
) {
    companion object {
        val NO_ERROR = Error(null, null);
    }
}