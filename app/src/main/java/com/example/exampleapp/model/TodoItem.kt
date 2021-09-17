package com.example.exampleapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoTable")
data class TodoItem(
    val title: String,
    val description: String = "",
    val important: Boolean = false,
    val completed: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
