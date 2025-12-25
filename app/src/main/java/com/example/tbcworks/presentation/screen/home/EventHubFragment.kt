package com.example.tbcworks.presentation.screen.home

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.tbcworks.databinding.FragmentEventHubBinding
import com.example.tbcworks.presentation.common.BaseFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screen.home.adapter.CategoryAdapter
import com.example.tbcworks.presentation.screen.home.adapter.FAQAdapter
import com.example.tbcworks.presentation.screen.home.adapter.ParentAdapter
import com.example.tbcworks.presentation.screen.home.adapter.TrendingAdapter
import com.example.tbcworks.presentation.screen.home.adapter.UpcomingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.toString

@AndroidEntryPoint
class EventHubFragment : BaseFragment<FragmentEventHubBinding>(
    FragmentEventHubBinding::inflate
) {
    private val viewModel: EventHubViewModel by viewModels()

    private val categoryAdapter by lazy {
        CategoryAdapter(
            onClick = { category ->
                val bundle = Bundle().apply {
                    putString("selectedCategory", category)
                }
                parentFragmentManager.setFragmentResult("categoryRequestKey", bundle)

                // Navigate to BrowseByCategoryFragment
                findNavController().navigate(
                    EventHubFragmentDirections.actionEventHubFragmentToBrowseByCategoryFragment()
                )

            }
        )
    }

    private val upcomingAdapter by lazy {
        UpcomingAdapter(
            onEventClick = { event ->
                // Navigate to EventDetailsFragment with eventId argument
                val action = EventHubFragmentDirections
                    .actionEventHubFragmentToEventDetailsFragment(event.id.toString())
                findNavController().navigate(action)
            }
        )
    }

    private val trendingAdapter by lazy {
        TrendingAdapter(
            onEventClick = { event ->
                val action = EventHubFragmentDirections
                    .actionEventHubFragmentToEventDetailsFragment(event.id.toString())
                findNavController().navigate(action)
            }
        )
    }

    override fun listeners() = with(binding){
        tvViewALl.setOnClickListener {
            findNavController().navigate(EventHubFragmentDirections.actionEventHubFragmentToBrowseEventFragment2())
        }
        btnNotifications.setOnClickListener {
            findNavController().navigate(EventHubFragmentDirections.actionEventHubFragmentToNotificationFragment())
        }
    }
    private val faqAdapter by lazy {
        FAQAdapter()
    }


    override fun bind() {
        setup()
    }
     private fun setup() {
        setupRecycler()
        observeState()
        observeSideEffects()

        viewModel.onEvent(EventHubContract.Event.LoadData)
    }

    private fun setupRecycler() = with(binding) {
        rvUpcomingEvents.adapter = upcomingAdapter
        rvUpcomingEvents.layoutManager = LinearLayoutManager(requireContext())

        rvBrowseByCategory.adapter = categoryAdapter
        rvBrowseByCategory.layoutManager = GridLayoutManager(requireContext(), 3)

        rvTrendingEvents.adapter = trendingAdapter
        rvTrendingEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        rvFaq.adapter = faqAdapter
        rvFaq.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeState() {
        collectStateFlow(viewModel.uiState) { state ->
            binding.progressBar.isVisible = state.isLoading
            upcomingAdapter.submitList(state.upcomingEvents)
            categoryAdapter.submitList(state.categories)
            trendingAdapter.submitList(state.trendingEvents)
            faqAdapter.submitList(state.faqs)

        }
    }

    private fun observeSideEffects() {
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is EventHubContract.SideEffect.NavigateToEvent -> {

                }
                is EventHubContract.SideEffect.NavigateToCategory -> {
                    // navigate to category screen
                }
            }
        }
    }
}
