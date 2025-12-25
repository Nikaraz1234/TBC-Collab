package com.example.tbcworks.domain.repository

import com.example.tbcworks.data.model.auth.sign_up.SendOtpResponseDto
import com.example.tbcworks.data.model.auth.sign_up.VerifyOtpResponseDto
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.auth.SignUp
import com.example.tbcworks.domain.model.auth.SignUpResponse
import com.example.tbcworks.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    fun signUp(user: SignUp): Flow<Resource<SignUpResponse>>
    fun sendOtp(phoneNumber: String): Flow<Resource<String>>
    fun verifyOtp(phoneNumber: String, otp: String): Flow<Resource<String>>

}