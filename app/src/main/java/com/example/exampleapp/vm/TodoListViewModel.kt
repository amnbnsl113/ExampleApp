package com.example.exampleapp.vm

import androidx.lifecycle.*
import com.example.exampleapp.data.remote.UserService
import com.example.exampleapp.model.StateModel
import com.example.exampleapp.model.UserList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    val userService: UserService
) : ViewModel() {
    val liveData: LiveData<String> = liveData {
        for (i in 1..10) {
            delay(2000)
            emit("Index:${i}")
        }
    }

    private val _stateModelLiveData = MutableLiveData<StateModel<UserList>>();
    val stateModelLiveData: LiveData<StateModel<UserList>> = _stateModelLiveData;

    private suspend fun networkCall(): UserList? {
        return withContext(Dispatchers.IO) {
            delay(1000)
            userService.getUserList().execute().body()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _stateModelLiveData.value = StateModel(isLoading = true)
            val response = networkCall();
            _stateModelLiveData.value =
                StateModel(response)
        }
    }
}