package com.example.exampleapp

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.ticker
import java.util.*
import kotlin.collections.ArrayList

class TodoListViewModel : ViewModel() {
    private val listLiveData: MutableLiveData<ArrayList<String>> by lazy {
        MutableLiveData(ArrayList())
    }

    fun getList(): LiveData<ArrayList<String>> {
        return listLiveData;
    }

    fun addData() {
        val timer = object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                addValue("Finished");
            }

            override fun onTick(p0: Long) {
                Log.e("pass", "onTick+${p0}")
                addValue(p0.toString());
            }
        }
        timer.start()
    }

    fun addValue(value: String) {
        val list = listLiveData.value;
        list?.add(value);
        listLiveData.value = list;
    }

}