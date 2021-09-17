package com.example.exampleapp.model

import com.example.exampleapp.model.ErrorMessage.Companion.NO_ERROR


data class StateModel<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: ErrorMessage = NO_ERROR,
)

data class ErrorMessage(
    val isError: Boolean,
    val errorMessage: String?
) {
    companion object {
        val NO_ERROR = ErrorMessage(false, null);
        val NO_DATA = ErrorMessage(true, "No Data Found");
    }
}