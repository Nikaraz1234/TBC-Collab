package com.example.tbcworks.domain.model.event


data class AgendaItem(
    val startTime: String?,
    val duration: String?,
    val title: String?,
    val description: String?,
    val activityType: String?,
    val activityLocation: String?
)