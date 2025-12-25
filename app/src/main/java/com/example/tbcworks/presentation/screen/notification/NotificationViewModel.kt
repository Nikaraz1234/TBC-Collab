package com.example.tbcworks.presentation.screen.notification

import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.domain.usecase.notification.GetNotificationsUseCase
import com.example.tbcworks.domain.usecase.notification.MarkNotificationAsReadUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.mapper.toPresentation
import com.example.tbcworks.presentation.screen.notification.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase
) : BaseViewModel<
        NotificationContract.State,
        NotificationContract.SideEffect,
        NotificationContract.Intent>(
    initialState = NotificationContract.State()
) {

    fun onIntent(intent: NotificationContract.Intent) {
        when (intent) {
            NotificationContract.Intent.LoadNotifications ->  loadNotifications()

            is NotificationContract.Intent.NotificationClicked -> {
                markNotificationAsRead(intent.notification.id)
                sendSideEffect(
                    NotificationContract.SideEffect.OpenNotification(intent.notification)
                )
            }
        }
    }

    private fun markNotificationAsRead(notificationId: Int) {
        handleResponse(
            apiCall = { markNotificationAsReadUseCase(notificationId) },
            onSuccess = {
                // Update local state to mark notification as read
                setState {
                    val updatedList = notifications.map { notification ->
                        if (notification.id == notificationId) notification.copy(isRead = true)
                        else notification
                    }
                    copy(notifications = updatedList)
                }
            },
            onError = { message ->
                sendSideEffect(NotificationContract.SideEffect.ShowError(message ?: "Error marking notification as read"))
            },
            onLoading = {
                // Optional: handle loading if needed
            }
        )
    }

    private fun loadNotifications() {
        handleResponse(
            apiCall = { getNotificationsUseCase() },

            onLoading = {
                setState { copy(isLoading = true, errorMessage = null) }
            },

            onSuccess = { notifications ->
                setState {
                    copy(
                        notifications = notifications.map { it.toPresentation() } ,
                        isLoading = false
                    )
                }
            },

            onError = { message ->
                setState { copy(isLoading = false, errorMessage = message) }
                sendSideEffect(NotificationContract.SideEffect.ShowError(message))
            }
        )
    }
}
