package com.example.tbcworks.domain.usecase.user

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.user.User
import com.example.tbcworks.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resource<User>> {
        return userRepository.getUserInfo()
    }
}