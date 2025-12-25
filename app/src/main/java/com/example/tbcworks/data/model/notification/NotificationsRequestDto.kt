package com.example.tbcworks.data.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationsRequestDto(
    val registerConfirmation: Boolean,
    val dailyReminder: Boolean,
    val hourReminder: Boolean,
    val waitlistUpdates: Boolean,
    val eventUpdated: Boolean
)