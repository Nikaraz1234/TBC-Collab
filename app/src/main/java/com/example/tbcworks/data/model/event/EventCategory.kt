package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class EventCategory(
    val name: String,
    val logourl: String
)