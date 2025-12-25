package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.service.NotificationService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.notification.Notification
import com.example.tbcworks.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val service: NotificationService,
    private val handleResponse: HandleResponse
) : NotificationRepository {

    override fun getNotifications(): Flow<Resource<List<Notification>>> {
        return handleResponse.safeApiCall {
            service.getNotifications()
        }.map { resource ->
            resource.asResource { list ->
                list.map { it.toDomain() }
            }
        }
    }

    override fun markNotificationAsRead(notificationId: Int): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            service.markNotificationAsRead(notificationId)
        }
    }

}