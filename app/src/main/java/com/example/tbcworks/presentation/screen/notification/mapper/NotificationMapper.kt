package com.example.tbcworks.presentation.screen.notification.mapper

import com.example.tbcworks.data.model.notification.NotificationResponseDto
import com.example.tbcworks.domain.model.notification.Notification
import com.example.tbcworks.presentation.screen.notification.model.NotificationModel

fun Notification.toPresentation(): NotificationModel {
    return NotificationModel(
        id = this.id,
        userId = this.userId,
        eventId = this.eventId,
        title = this.title,
        message = this.message,
        type = this.type,
        isRead = this.isRead,
        createdAt = this.createdAt
    )
}