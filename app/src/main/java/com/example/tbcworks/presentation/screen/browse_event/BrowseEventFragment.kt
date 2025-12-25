package com.example.tbcworks.presentation.screen.browse_event

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentBrowseEventBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screen.browse_event.adapter.CategoryAdapter
import com.example.tbcworks.presentation.screen.browse_event.adapter.EventAdapter
import com.example.tbcworks.presentation.screen.home.EventHubFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowseEventFragment : BaseFragment<FragmentBrowseEventBinding>(
    FragmentBrowseEventBinding::inflate
) {

    private val viewModel: BrowseEventViewModel by viewModels()

    private val eventAdapter: EventAdapter by lazy {
        EventAdapter(
            onItemClick = { event ->
                val action = BrowseEventFragmentDirections
                    .actionBrowseEventFragment2ToEventDetailsFragment(event.id.toString())
                findNavController().navigate(action)
            }
        )
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter { categoryPosition ->
            val categoryName = viewModel.uiState.value.categories[categoryPosition]
            viewModel.onEvent(BrowseEventContract.Event.CategorySelected(categoryName))
        }
    }

    override fun bind() {
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.rvEvents.adapter = eventAdapter
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())

        binding.rvCategories.adapter = categoryAdapter
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.btnFilters.setOnClickListener {
            showFiltersBottomSheet()
        }
    }

    override fun listeners() {
        binding.etSearch.addTextChangedListener { text ->
            viewModel.onEvent(BrowseEventContract.Event.SearchQueryChanged(text.toString()))
        }
    }

    private fun initObservers() {
        collectStateFlow(viewModel.uiState) { state ->
            eventAdapter.submitList(state.filteredEvents)
            categoryAdapter.submitList(state.categories)
        }

        collectFlow(viewModel.sideEffect) { effect ->
            if (effect is BrowseEventContract.SideEffect.ShowError) {
                // TODO: show toast/snackbar
            }
        }
    }

    private fun showFiltersBottomSheet() {
        val bottomSheet = FiltersBottomSheet { online, offline, available, full ->

            val locationTypes = mutableListOf<String>()
            if (online) locationTypes.add("Online")
            if (offline) locationTypes.add("Offline")

            val capacityAvailability = mutableListOf<String>()
            if (available) capacityAvailability.add("Available")
            if (full) capacityAvailability.add("Full")

            val filter = com.example.tbcworks.domain.model.event.EventFilter(
                locationTypes = locationTypes.ifEmpty { null },
                capacityAvailability = capacityAvailability.ifEmpty { null },
                search = viewModel.uiState.value.searchQuery.takeIf { it.isNotBlank() }
            )

            viewModel.onEvent(BrowseEventContract.Event.FiltersApplied(filter))
        }

        bottomSheet.show(parentFragmentManager, "FiltersBottomSheet")
    }
}
