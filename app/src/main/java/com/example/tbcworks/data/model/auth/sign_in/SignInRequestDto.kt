package com.example.tbcworks.data.model.auth.sign_in

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDto(val email: String, val password: String)
