package com.example.tbcworks.presentation.mapper

import com.example.tbcworks.domain.model.user.User
import com.example.tbcworks.presentation.model.user.UserModel

fun User.toPresentation(): UserModel =
    UserModel(
        firstName = firstName,
        lastName = lastName,
        role = role,
        department = department
    )