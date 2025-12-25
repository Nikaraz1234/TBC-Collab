package com.example.tbcworks.data.model.auth.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpResponseDto(
    val message: String?
)
