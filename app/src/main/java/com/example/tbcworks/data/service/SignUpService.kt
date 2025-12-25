package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.auth.sign_up.SendOtpRequestDto
import com.example.tbcworks.data.model.auth.sign_up.SendOtpResponseDto
import com.example.tbcworks.data.model.auth.sign_up.SignUpRequestDto
import com.example.tbcworks.data.model.auth.sign_up.SignUpResponseDto
import com.example.tbcworks.data.model.auth.sign_up.VerifyOtpRequestDto
import com.example.tbcworks.data.model.auth.sign_up.VerifyOtpResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("auth/register")
    suspend fun signUp(@Body request: SignUpRequestDto): SignUpResponseDto

    @POST("auth/send-otp")
    suspend fun sendOtp(
        @Body request: SendOtpRequestDto
    ): String

    @POST("auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequestDto
    ): String
}