package com.example.tbcworks.presentation.screen.my_events

import com.example.tbcworks.presentation.model.EventModel

object MyEventsContract {

    data class State(
        val events: List<EventModel> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class SideEffect {
        data class ShowError(val message: String) : SideEffect()
        data class OpenEventDetail(val event: EventModel) : SideEffect()
    }

    sealed class Intent {
        object LoadEvents : Intent()
        data class EventClicked(val event: EventModel) : Intent()
    }
}