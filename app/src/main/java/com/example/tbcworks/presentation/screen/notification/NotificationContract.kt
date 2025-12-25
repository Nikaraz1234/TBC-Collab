package com.example.tbcworks.presentation.screen.notification

import com.example.tbcworks.presentation.screen.notification.model.NotificationModel

object NotificationContract {

    data class State(
        val isLoading: Boolean = false,
        val notifications: List<NotificationModel> = emptyList(),
        val errorMessage: String? = null
    )

    sealed interface Intent {
        object LoadNotifications : Intent
        data class NotificationClicked(val notification: NotificationModel) : Intent
    }

    sealed interface SideEffect {
        data class ShowError(val message: String) : SideEffect
        data class OpenNotification(val notification: NotificationModel) : SideEffect
    }
}
