package com.example.exampleapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampleapp.data.local.AppDatabase
import com.example.exampleapp.model.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    val database: AppDatabase
) : ViewModel() {

    private var title: CharSequence? = null;
    private val _channel = Channel<AddTodoEvents>();
    val uiEventsFlow = _channel.receiveAsFlow()

    fun submitTodoItem() {
        viewModelScope.launch {
            val todoItem = TodoItem(title = title.toString())
            database.todoItemDao().addTask(todoItem)
            _channel.send(AddTodoEvents.TODO_TEM_ADDED)
        }
    }

    fun textChanged(text: CharSequence?) {
        title = text;
    }

    sealed class AddTodoEvents {
        object TODO_TEM_ADDED : AddTodoEvents();
    }
}