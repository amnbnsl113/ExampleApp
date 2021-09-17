package com.example.exampleapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exampleapp.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todoTable")
    fun getTasks(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(todoItem: TodoItem)

    @Query("DELETE FROM todoTable")
    suspend fun flushTable()
}