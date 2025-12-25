package com.example.tbcworks.domain.usecase.auth

import com.example.tbcworks.data.model.auth.sign_up.SendOtpResponseDto
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val repository: SignUpRepository
) {
    fun execute(phoneNumber: String): Flow<Resource<String>> {
        return repository.sendOtp(phoneNumber)
    }
}