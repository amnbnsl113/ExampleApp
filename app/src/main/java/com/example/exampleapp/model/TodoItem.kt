package com.example.exampleapp.model

import androidx.room.Entity

@Entity(tableName = "todoTable")
data class TodoItem(
    val id: String,
    val title: String,
    val description: String,
    val important: Boolean,
    val completed: Boolean
)
