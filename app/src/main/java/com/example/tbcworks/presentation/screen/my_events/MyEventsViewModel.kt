package com.example.tbcworks.presentation.screen.my_events

import com.example.tbcworks.domain.usecase.event.GetMyRegisteredEventsUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyEventsViewModel @Inject constructor(
    private val getMyRegisteredEventsUseCase: GetMyRegisteredEventsUseCase
) : BaseViewModel<
        MyEventsContract.State,
        MyEventsContract.SideEffect,
        MyEventsContract.Intent>(
    initialState = MyEventsContract.State()
) {

            init {
                loadEvents()
            }
    fun onIntent(intent: MyEventsContract.Intent) {
        when (intent) {
            is MyEventsContract.Intent.LoadEvents -> loadEvents()
            is MyEventsContract.Intent.EventClicked -> {
                sendSideEffect(MyEventsContract.SideEffect.OpenEventDetail(intent.event))
            }

        }
    }

    private fun loadEvents() {
        handleResponse(
            apiCall = { getMyRegisteredEventsUseCase() },
            onLoading = { setState { copy(isLoading = true, errorMessage = null) } },
            onSuccess = { events ->
                setState { copy(events = events.map { it.toPresentation() }, isLoading = false) }
            },
            onError = { message ->
                setState { copy(isLoading = false, errorMessage = message) }
                sendSideEffect(MyEventsContract.SideEffect.ShowError(message))
            }
        )
    }
}