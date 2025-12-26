    package com.example.tbcworks.data.model.notification

    import kotlinx.serialization.SerialName
    import kotlinx.serialization.Serializable


    @Serializable
    data class NotificationResponseDto(
        val id: Int,
        val userId: Int,
        val eventId: Int?,
        val title: String,
        val message: String,
        val type: NotificationType,
        val isRead: Boolean,
        val createdAt: String
    )

    @Serializable
    enum class NotificationType {
        @SerialName("RegisterConfirmation") RegisterConfirmation,
        @SerialName("DailyReminder") DailyReminder,
        @SerialName("HourReminder") HourReminder,
        @SerialName("WaitlistUpdate") WaitlistUpdate,
        @SerialName("EventUpdated") EventUpdated
    }