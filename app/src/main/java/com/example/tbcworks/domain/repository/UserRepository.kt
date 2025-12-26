package com.example.tbcworks.domain.repository


import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserInfo(): Flow<Resource<User>>
}