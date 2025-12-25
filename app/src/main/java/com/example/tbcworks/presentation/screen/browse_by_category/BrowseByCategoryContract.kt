package com.example.tbcworks.presentation.screen.browse_by_category

import com.example.tbcworks.presentation.model.EventModel

object BrowseByCategoryContract {

    // UI State
    data class State(
        val events: List<EventModel> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    // Side effects (optional, e.g., show toast)
    sealed class SideEffect {
        data class ShowError(val message: String) : SideEffect()
    }

    // Intents / actions
    sealed class Intent {
        data class LoadEvents(val category: String) : Intent()
    }
}