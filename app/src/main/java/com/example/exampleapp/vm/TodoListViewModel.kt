package com.example.exampleapp.vm

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.example.exampleapp.data.RemoteRepositoryManager
import com.example.exampleapp.model.UserList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList

class TodoListViewModel : ViewModel() {
    val liveData: LiveData<UserList> = liveData {
        emitSource(networkCall())
    }

    private fun networkCall(): LiveData<UserList> {
        return liveData(Dispatchers.IO) {
            RemoteRepositoryManager.userService.getUserList().execute().body()?.let {
                emit(it)
            }
        }
    }
}