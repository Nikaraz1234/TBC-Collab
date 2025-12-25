package com.example.tbcworks.data.model.auth.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequestDto(
    val phoneNumber: String?,
    val otp: String?
)
