package com.example.tbcworks.presentation.screen.home

import android.util.Log
import com.example.tbcworks.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.domain.usecase.event.GetEventsUseCase
import com.example.tbcworks.presentation.mapper.toPresentation
import com.example.tbcworks.presentation.screen.home.model.CategoryModel
import com.example.tbcworks.presentation.screen.home.model.QaItem

@HiltViewModel
class EventHubViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : BaseViewModel<
        EventHubContract.State,
        EventHubContract.SideEffect,
        EventHubContract.Event
        >(initialState = EventHubContract.State()) {

    companion object {
        val staticFaqs = listOf(
            QaItem(
                question = "How do I register for an event?",
                answer = "You can register by clicking the 'Register' button on the event details page."
            ),
            QaItem(
                question = "Can I cancel my registration?",
                answer = "Yes, you can cancel your registration up to 24 hours before the event."
            ),
            QaItem(
                question = "Are events free to attend?",
                answer = "Most events are free, but some premium workshops may have a fee."
            )
        )

    }

    init {
        setState {
            copy(
                faqs = staticFaqs
            )
        }

        loadData()
    }

    fun onEvent(intent: EventHubContract.Event) {
        when (intent) {
            EventHubContract.Event.LoadData -> loadData()

            is EventHubContract.Event.ApplyFilter -> {
                setState { copy(selectedFilter = intent.filter) }
                loadData()
            }

            EventHubContract.Event.ClearFilter -> {
                setState { copy(selectedFilter = null) }
                loadData()
            }

            is EventHubContract.Event.CategoryClicked -> {
                sendSideEffect(
                    EventHubContract.SideEffect.NavigateToCategory(intent.category)
                )
            }

            is EventHubContract.Event.EventClicked -> {
                sendSideEffect(
                    EventHubContract.SideEffect.NavigateToEvent(intent.eventId)
                )
            }
        }
    }

    private fun loadData() {
        handleResponse(
            apiCall = { getEventsUseCase() },
            onLoading = {
                setState { copy(isLoading = true) }
            },
            onSuccess = { events ->
                val uiEvents = events.map { it.toPresentation() }

                // LOG HERE
                Log.d("EventHubViewModel", "Mapped events: ${uiEvents.joinToString { it.title ?: "no title" }}")

                val categories = events
                    .groupBy { it.category?.name.orEmpty() }
                    .map { (categoryName, eventsInCategory) ->
                        CategoryModel(
                            category = categoryName,
                            eventCount = eventsInCategory.size,
                            logoUrl = eventsInCategory.firstOrNull()?.category?.logourl.orEmpty()
                        )
                    }

                setState {
                    copy(
                        isLoading = false,
                        upcomingEvents = uiEvents.sortedBy { it.date?.startDate }.take(3),
                        trendingEvents = uiEvents.take(5),
                        categories = categories,
                        faqs = staticFaqs
                    )
                }

                // LOG STATE
                Log.d("EventHubViewModel", "State after loadData: upcomingEvents=${uiEvents.size}, categories=${categories.size}")
            },
            onError = {
                setState { copy(isLoading = false) }
                Log.e("EventHubViewModel", "Error loading events: $it")
            }
        )
    }

}
