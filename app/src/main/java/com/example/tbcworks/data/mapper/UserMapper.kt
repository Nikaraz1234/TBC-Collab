package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.model.user.UserDto
import com.example.tbcworks.domain.model.user.User

fun UserDto.toDomain(): User =
    User(
        firstName = firstName,
        lastName = lastName,
        role = role,
        department = department
    )