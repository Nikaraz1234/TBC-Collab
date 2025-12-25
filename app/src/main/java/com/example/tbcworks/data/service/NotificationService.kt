package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.notification.NotificationResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationService {
    @GET("notification")
    suspend fun getNotifications() : List<NotificationResponseDto>

    @POST("notification/mark-as-read/{notificationId}")
    suspend fun markNotificationAsRead(@Path("notificationId") notificationId: Int)
}