package com.example.exampleapp.vm

import androidx.lifecycle.*
import com.example.exampleapp.data.local.AppDatabase
import com.example.exampleapp.data.remote.UserService
import com.example.exampleapp.model.ErrorMessage
import com.example.exampleapp.model.StateModel
import com.example.exampleapp.model.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val userService: UserService,
    private val appDatabase: AppDatabase
) : ViewModel() {

    private val _uiOperationsChannel = Channel<TodoViewEvent>();
    val uiOperationsFlow = _uiOperationsChannel.receiveAsFlow();


    val todoListLiveData: LiveData<StateModel<List<TodoItem>>> =
        appDatabase.todoItemDao().getTasks().asLiveData().switchMap {
            liveData {
                emit(convertToStateModel(it))
            }
        }

    private fun convertToStateModel(it: List<TodoItem>): StateModel<List<TodoItem>> {
        val errorMessage: ErrorMessage =
            if (it.isEmpty()) ErrorMessage.NO_DATA else ErrorMessage.NO_ERROR
        return StateModel(it, error = errorMessage)
    }

    fun flushData() {
        viewModelScope.launch {
            delay(3000)
            appDatabase.todoItemDao().flushTable()
        }
    }

    fun addNewTask() = viewModelScope.launch {
        _uiOperationsChannel.send(TodoViewEvent.ADD_TODO_EVENT)
    }




//    private val _stateModelLiveData = MutableLiveData<StateModel<UserList>>();
//    val stateModelLiveData: LiveData<StateModel<UserList>> = _stateModelLiveData;


//    val liveData: LiveData<String> = liveData {
//        for (i in 1..10) {
//            delay(2000)
//            emit("Index:${i}")
//        }
//    }
//    private suspend fun fetchUserList(): UserList? {
//        return withContext(Dispatchers.IO) {
//            delay(1000)
//            userService.getUserList().execute().body()
//        }
//    }
//
//    fun refreshData() {
//        viewModelScope.launch {
//            _stateModelLiveData.value = StateModel(isLoading = true)
//            val response = fetchUserList();
//            _stateModelLiveData.value =
//                StateModel(response)
//        }
//    }

    fun addData() {
    }

    //        viewModelScope.launch {
//            for (i in 1..10) {
//                delay(2000)
//                appDatabase.todoItemDao().addTask(TodoItem("Title:${i}"))
//            }
//        }
//    }
    sealed class TodoViewEvent {
        object ADD_TODO_EVENT : TodoViewEvent();
    }

}