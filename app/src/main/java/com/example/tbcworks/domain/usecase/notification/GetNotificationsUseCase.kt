package com.example.tbcworks.domain.usecase.notification

import com.example.tbcworks.data.model.notification.NotificationResponseDto
import com.example.tbcworks.data.model.notification.NotificationsRequestDto
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.notification.Notification
import com.example.tbcworks.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(): Flow<Resource<List<Notification>>> {
        return repository.getNotifications()
    }
}