package com.example.tbcworks.domain.model.event

data class Capacity(
    val maxCapacity: Int,
    val minParticipants: Int? = null,
    val enableWaitlist: Boolean? = null,
    val waitlistCapacity: Int? = null,
    val autoApprove: Boolean? = null
)