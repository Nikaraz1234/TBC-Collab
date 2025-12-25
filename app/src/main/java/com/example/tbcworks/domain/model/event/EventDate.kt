package com.example.tbcworks.domain.model.event

import com.example.tbcworks.data.model.event.EventTypeEnum
import kotlinx.datetime.LocalDateTime


data class EventDate(
    val eventType: EventTypeEnum,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val registerDeadline: LocalDateTime?
)