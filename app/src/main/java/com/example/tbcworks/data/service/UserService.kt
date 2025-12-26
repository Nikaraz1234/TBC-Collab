package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.user.UserDto
import retrofit2.http.GET


interface UserService {

    @GET("users/me")
    suspend fun getUserInfo(): UserDto
}