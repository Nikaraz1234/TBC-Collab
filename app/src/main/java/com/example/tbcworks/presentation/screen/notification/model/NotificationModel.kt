package com.example.tbcworks.presentation.screen.notification.model

import com.example.tbcworks.data.model.notification.NotificationType

data class NotificationModel(
    val id: Int,
    val userId: Int,
    val eventId: Int?,
    val title: String,
    val message: String,
    val type: NotificationType,
    val isRead: Boolean,
    val createdAt: String
)