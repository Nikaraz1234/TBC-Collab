package com.example.tbcworks.domain.usecase.notification

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkNotificationAsReadUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(notificationId: Int): Flow<Resource<Unit>> {
        return repository.markNotificationAsRead(notificationId)
    }
}