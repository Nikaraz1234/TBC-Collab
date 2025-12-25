package com.example.tbcworks.data.model.event

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class DateDto(
    val eventType: EventTypeEnum = EventTypeEnum.Hybrid,
    val startDate: Instant,
    val endDate: Instant,
    val registerDeadline: Instant? = null
)
