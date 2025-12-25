package com.example.tbcworks.domain.model.notification

import com.example.tbcworks.data.model.notification.NotificationType
import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: Int,
    val userId: Int,
    val eventId: Int?,
    val title: String,
    val message: String,
    val type: NotificationType,
    val isRead: Boolean,
    val createdAt: String
)