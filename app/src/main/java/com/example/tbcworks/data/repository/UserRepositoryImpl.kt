package com.example.tbcworks.data.repository

import android.util.Log
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.service.UserService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.user.User
import com.example.tbcworks.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val handleResponse: HandleResponse
) : UserRepository {

    override fun getUserInfo(): Flow<Resource<User>> {
        return handleResponse
            .safeApiCall {
                Log.d("UserRepo", "API called")
                userService.getUserInfo()
            }
            .map { resource ->
                resource.asResource { dto -> dto.toDomain() }
            }
    }
}