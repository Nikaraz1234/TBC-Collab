package com.example.tbcworks.presentation.screen.browse_event

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.event.EventFilter
import com.example.tbcworks.domain.usecase.event.GetEventsUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowseEventViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : BaseViewModel<BrowseEventContract.State, BrowseEventContract.SideEffect, BrowseEventContract.Event>(
    BrowseEventContract.State()
) {

    private var selectedCategory: String? = null
    private var activeFilter: EventFilter? = null

    init {
        loadCategories()
        loadEvents()
    }

    private fun loadCategories() {
        val categories = listOf(
            "All",
            "TeamBuilding",
            "Sports",
            "Workshops",
            "HappyFridays",
            "Cultural",
            "Wellness"
        )
        setState { copy(categories = categories) }
    }

    private fun loadEvents() {
        handleResponse(
            apiCall = { getEventsUseCase(activeFilter) },
            onLoading = { setState { copy(isLoading = true) } },
            onSuccess = { events ->
                val mapped = events.map { it.toPresentation() }
                setState {
                    copy(
                        events = mapped,
                        filteredEvents = mapped,
                        isLoading = false
                    )
                }
            },
            onError = { message ->
                sendSideEffect(BrowseEventContract.SideEffect.ShowError(message ?: "Unknown error"))
                setState { copy(isLoading = false) }
            }
        )
    }

    fun onEvent(event: BrowseEventContract.Event) {
        when (event) {
            is BrowseEventContract.Event.SearchQueryChanged -> {
                setState { copy(searchQuery = event.query) }
                applySearchFilter() // filter locally for search only
            }
            is BrowseEventContract.Event.CategorySelected -> {
                selectedCategory = event.category
                applySearchFilter() // apply search + category locally if needed
            }
            is BrowseEventContract.Event.FiltersApplied -> {
                activeFilter = event.filter
                loadEvents() // fetch filtered events from use case
            }
        }
    }

    private fun applySearchFilter() {
        val query = uiState.value.searchQuery.lowercase()

        val filtered = uiState.value.events.filter { event ->

            val matchesQuery =
                query.isBlank() ||
                        event.title?.lowercase()?.contains(query) == true

            val matchesCategory =
                selectedCategory.isNullOrBlank() || selectedCategory.equals("All", ignoreCase = true) ||
                        selectedCategory.equals(event.category?.name.toString(), ignoreCase = true)

            matchesQuery && matchesCategory
        }

        setState { copy(filteredEvents = filtered) }
    }

}
