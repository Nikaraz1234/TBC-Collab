package com.example.tbcworks.presentation.screen.home

import com.example.tbcworks.domain.model.event.EventFilter
import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.screen.home.model.CategoryModel
import com.example.tbcworks.presentation.screen.home.model.QaItem
import com.example.tbcworks.presentation.screen.home.model.Section

object EventHubContract {

    data class State(
        val isLoading: Boolean = false,
        val upcomingEvents: List<EventModel> = emptyList(),
        val trendingEvents: List<EventModel> = emptyList(),
        val selectedFilter: EventFilter? = null,
        val categories: List<CategoryModel> = emptyList(),
        val faqs: List<QaItem> = emptyList()
    )
    sealed interface Event {
        data object LoadData : Event
        data class ApplyFilter(val filter: EventFilter) : Event
        data object ClearFilter : Event
        data class CategoryClicked(val category: String) : Event
        data class EventClicked(val eventId: String) : Event
    }

    sealed interface SideEffect {
        data class NavigateToEvent(val eventId: String) : SideEffect
        data class NavigateToCategory(val category: String) : SideEffect
    }
}

