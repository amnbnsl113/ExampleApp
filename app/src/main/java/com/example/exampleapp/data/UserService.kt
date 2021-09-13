package com.example.exampleapp.data

import com.example.exampleapp.model.UserList
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("/api/users?page=2")
    fun getUserList(): Call<UserList>
}