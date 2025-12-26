package com.example.tbcworks.presentation.screen.event_detail

import com.example.tbcworks.domain.usecase.event.CancelRegistrationOnEventUseCase
import com.example.tbcworks.domain.usecase.event.GetEventByIdUseCase
import com.example.tbcworks.domain.usecase.event.RegisterOnEventUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.mapper.toPresentation
import com.example.tbcworks.presentation.screen.event_detail.mapper.toUiString
import com.example.tbcworks.presentation.screen.home.mapper.toFormattedDateTime
import com.example.tbcworks.presentation.screen.home.mapper.toTimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val registerOnEventUseCase: RegisterOnEventUseCase,
    private val cancelRegistrationOnEventUseCase: CancelRegistrationOnEventUseCase
) : BaseViewModel<
        EventDetailsContract.State,
        EventDetailsContract.Effect,
        EventDetailsContract.Event
        >(initialState = EventDetailsContract.State()) {

    fun onEvent(event: EventDetailsContract.Event) {
        when (event) {
            is EventDetailsContract.Event.Load -> loadEvent(event.eventId)
            EventDetailsContract.Event.BackClicked ->
                sendSideEffect(EventDetailsContract.Effect.NavigateBack)
            EventDetailsContract.Event.RegisterClicked -> handleRegisterClick()
        }
    }

    private fun loadEvent(eventId: String) {
        handleResponse(
            apiCall = { getEventByIdUseCase(eventId) },
            onLoading = { setState { copy(isLoading = true, errorMessage = null) } },
            onSuccess = { event ->
                val status = event?.userStatus ?: "notRegistered"

                setState {
                    copy(
                        isLoading = false,
                        eventId = eventId,
                        title = event?.title.orEmpty(),
                        bannerUrl = event?.imgUrl.orEmpty(),
                        eventDate = event?.date?.let {
                            "${it.startDate.dayOfMonth} ${it.startDate.month.name.take(3)}, ${it.startDate.year}"
                        }.orEmpty(),
                        eventTime = event?.date?.toTimeRange().orEmpty(),
                        location = event?.location?.address?.toUiString().orEmpty(),
                        capacity = event?.capacity?.let { "${it.maxCapacity} Seats" }.orEmpty(),
                        isUserRegistered = status == "Approved",
                        isRegistrationOpen = status != "Closed" && status != "Registered",
                        registerCloseText = event?.date?.registerDeadline?.let {
                            "Registration closes on ${it.toFormattedDateTime()}"
                        }.orEmpty(),
                        aboutDescription = event?.description.orEmpty(),
                        agenda = event?.agenda?.map { it.toPresentation() } ?: emptyList(),
                        speakers = event?.speakers?.map { it.toPresentation() } ?: emptyList()
                    )
                }
            },
            onError = { message ->
                setState { copy(isLoading = false, errorMessage = message) }
                sendSideEffect(EventDetailsContract.Effect.ShowError(message))
            }
        )
    }

    private fun handleRegisterClick() {
        val state = uiState.value
        val eventId = state.eventId ?: run {
            sendSideEffect(EventDetailsContract.Effect.ShowError("Event ID not found"))
            return
        }

        // If registration is closed and user is not registered
        if (!state.isRegistrationOpen && !state.isUserRegistered) {
            sendSideEffect(EventDetailsContract.Effect.ShowMessage("Registration is closed"))
            return
        }

        if (state.isUserRegistered) {
            // Cancel registration
            handleResponse(
                apiCall = { cancelRegistrationOnEventUseCase(eventId.toInt()) },
                onLoading = { setState { copy(isLoading = true) } },
                onSuccess = {
                    setState { copy(isLoading = false, isUserRegistered = false, isRegistrationOpen = true) }
                    sendSideEffect(EventDetailsContract.Effect.ShowMessage("Registration cancelled"))
                },
                onError = { message ->
                    setState { copy(isLoading = false) }
                    sendSideEffect(EventDetailsContract.Effect.ShowError(message))
                }
            )
        } else {
            // Register
            handleResponse(
                apiCall = { registerOnEventUseCase(eventId.toInt()) },
                onLoading = { setState { copy(isLoading = true) } },
                onSuccess = {
                    setState { copy(isLoading = false, isUserRegistered = true, isRegistrationOpen = false) }
                    sendSideEffect(EventDetailsContract.Effect.ShowMessage("Successfully registered for the event"))
                },
                onError = { message ->
                    setState { copy(isLoading = false) }
                    sendSideEffect(EventDetailsContract.Effect.ShowError(message))
                }
            )
        }
    }
}
