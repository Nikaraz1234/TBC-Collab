package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.mapper.toRequestDto
import com.example.tbcworks.data.model.auth.sign_up.SendOtpRequestDto
import com.example.tbcworks.data.model.auth.sign_up.SendOtpResponseDto
import com.example.tbcworks.data.model.auth.sign_up.VerifyOtpRequestDto
import com.example.tbcworks.data.model.auth.sign_up.VerifyOtpResponseDto
import com.example.tbcworks.data.service.SignUpService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.auth.SignUp
import com.example.tbcworks.domain.model.auth.SignUpResponse
import com.example.tbcworks.domain.model.user.User
import com.example.tbcworks.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val service: SignUpService,
    private val handleResponse: HandleResponse
) : SignUpRepository {

    override fun signUp(user: SignUp): Flow<Resource<SignUpResponse>> {
        return handleResponse.safeApiCall {
            service.signUp(user.toRequestDto())
        }.map { it.asResource { response -> response.toDomain() } }
    }
    override fun sendOtp(phoneNumber: String): Flow<Resource<String>> {
        return handleResponse.safeApiCall {
            service.sendOtp(SendOtpRequestDto(phoneNumber))
        }.map { it.asResource { responseText ->
            // Extract the OTP or just return the message
            responseText // or parse it with Regex if needed
        } }
    }

    override fun verifyOtp(phoneNumber: String, otp: String): Flow<Resource<String>> {
        return handleResponse.safeApiCall {
            service.verifyOtp(VerifyOtpRequestDto(phoneNumber, otp))
        }.map { it.asResource { response -> response } }
    }


}