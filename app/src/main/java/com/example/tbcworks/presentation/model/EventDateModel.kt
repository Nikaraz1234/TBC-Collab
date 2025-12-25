package com.example.tbcworks.presentation.model

import com.example.tbcworks.data.model.event.EventTypeEnum
import kotlinx.datetime.LocalDateTime

data class EventDateModel(
    val eventType: EventTypeEnum,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val registerDeadline: LocalDateTime?
)