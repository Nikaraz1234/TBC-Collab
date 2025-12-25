package com.example.tbcworks.presentation.screen.browse_by_category

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.event.GetEventsByCategoryUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BrowseByCategoryViewModel @Inject constructor(
    private val getEventsByCategoryUseCase: GetEventsByCategoryUseCase
) : BaseViewModel<BrowseByCategoryContract.State, BrowseByCategoryContract.SideEffect, BrowseByCategoryContract.Intent>(
    BrowseByCategoryContract.State()
) {

    fun handleIntent(intent: BrowseByCategoryContract.Intent) {
        when (intent) {
            is BrowseByCategoryContract.Intent.LoadEvents -> loadEvents(intent.category)
        }
    }

    private fun loadEvents(category: String) {
        setState { copy(isLoading = true, error = null) }

        handleResponse(
            apiCall = { getEventsByCategoryUseCase(category) },
            onSuccess = { events ->
                val models = events.map { it.toPresentation() }
                setState { copy(events = models, isLoading = false) }
            },
            onError = { message ->
                setState { copy(isLoading = false, error = message) }
                sendSideEffect(BrowseByCategoryContract.SideEffect.ShowError(message))
            },
            onLoading = { /* optional, can show loading */ }
        )
    }
}
