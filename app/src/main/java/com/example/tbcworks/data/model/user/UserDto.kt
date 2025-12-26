package com.example.tbcworks.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val firstName: String,
    val lastName: String,
    val role: String,
    val department: String
)