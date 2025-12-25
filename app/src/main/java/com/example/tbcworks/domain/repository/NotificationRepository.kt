package com.example.tbcworks.domain.repository

import com.example.tbcworks.data.model.notification.NotificationResponseDto
import com.example.tbcworks.data.model.notification.NotificationsRequestDto
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.notification.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(): Flow<Resource<List<Notification>>>
    fun markNotificationAsRead(notificationId: Int): Flow<Resource<Unit>>
}