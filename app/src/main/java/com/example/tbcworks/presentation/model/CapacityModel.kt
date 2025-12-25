package com.example.tbcworks.presentation.model

data class CapacityModel(
    val maxCapacity: Int,
    val minParticipants: Int? = null,
    val enableWaitlist: Boolean? = null,
    val waitlistCapacity: Int? = null,
    val autoApprove: Boolean? = null
)