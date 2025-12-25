package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class SpeakerDto(
    val fullName: String? = null,
    val role: String? = null,
    val description: String? = null
)