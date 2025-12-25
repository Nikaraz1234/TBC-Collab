package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.model.auth.sign_up.SignUpRequestDto
import com.example.tbcworks.data.model.auth.sign_up.SignUpResponseDto
import com.example.tbcworks.domain.model.auth.SignUp
import com.example.tbcworks.domain.model.auth.SignUpResponse
import com.example.tbcworks.domain.model.user.User

fun SignUpResponseDto.toDomain(): SignUpResponse {
    return SignUpResponse(
        id = userId
    )
}

fun SignUp.toRequestDto(): SignUpRequestDto {
    return SignUpRequestDto(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        department = department,
        password = password,
        passwordConfirmation = confirmPassword
    )
}