package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class CapacityDto(
    val maxCapacity: Int,
    val minParticipants: Int? = null,
    val enableWaitlist: Boolean? = null,
    val waitlistCapacity: Int? = null,
    val autoApprove: Boolean? = null
)