package com.example.tbcworks.data.model.auth.sign_in

import kotlinx.serialization.Serializable


@Serializable
data class SignInResponseDto(val token: String)