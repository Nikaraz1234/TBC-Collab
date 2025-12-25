package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.model.notification.NotificationResponseDto
import com.example.tbcworks.domain.model.notification.Notification

fun NotificationResponseDto.toDomain(): Notification {
    return Notification(
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