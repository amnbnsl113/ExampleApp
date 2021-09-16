package com.example.exampleapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepositoryManager {
    companion object {
        private val baseUrl = "https://reqres.in/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val userService = retrofit.create(UserService::class.java)
    }
}