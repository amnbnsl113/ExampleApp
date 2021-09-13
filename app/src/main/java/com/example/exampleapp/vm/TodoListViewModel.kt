package com.example.exampleapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampleapp.data.RemoteRepositoryManager
import com.example.exampleapp.model.StateModel
import com.example.exampleapp.model.UserList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoListViewModel : ViewModel() {
//    val liveData: LiveData<UserList> = liveData {
//        emitSource(networkCall())
//    }


    private val _stateModelLiveData = MutableLiveData<StateModel<UserList>>();
    val stateModelLiveData: LiveData<StateModel<UserList>> = _stateModelLiveData;

    private suspend fun networkCall(): UserList? {
        return withContext(Dispatchers.IO) {
            delay(3000)
            RemoteRepositoryManager.userService.getUserList().execute().body()
        }
    }

    fun refreshData() {
        viewModelScope.launch (Dispatchers.Main){
            _stateModelLiveData.value = StateModel(null, true, false, null)
            val response = networkCall();
            _stateModelLiveData.value =
                StateModel(null, false, true, "Some problem occurred")
        }
    }
}