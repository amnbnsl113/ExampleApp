package com.example.exampleapp.model

import com.google.gson.annotations.SerializedName

data class UserList(
    @SerializedName("data")
    val list: List<TodoItem>,
    val page: Int
)
