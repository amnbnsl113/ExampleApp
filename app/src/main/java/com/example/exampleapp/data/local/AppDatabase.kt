package com.example.exampleapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exampleapp.model.TodoItem

@Database(entities = arrayOf(TodoItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao
}