package com.example.tbcworks.presentation.screen.my_events

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentMyEventsBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screen.home.EventHubFragmentDirections
import com.example.tbcworks.presentation.screen.my_events.adapter.MyEventAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.toString

@AndroidEntryPoint
class MyEventsFragment : BaseFragment<FragmentMyEventsBinding>(
    FragmentMyEventsBinding::inflate
) {

    private val viewModel: MyEventsViewModel by viewModels()

    private val eventsAdapter: MyEventAdapter by lazy {
        MyEventAdapter { event ->
            val action = MyEventsFragmentDirections
                .actionMyEventsFragmentToEventDetailsFragment(event.id.toString())
            findNavController().navigate(action)        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeState()
        observeSideEffect()
        viewModel.onIntent(MyEventsContract.Intent.LoadEvents)
    }

    private fun setupRecyclerView() {
        binding.rvMyEvents.apply {
            adapter = eventsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeState() {
        collectStateFlow(viewModel.uiState) { state ->

            binding.progressBar.isVisible = state.isLoading

            eventsAdapter.submitList(state.events)

            binding.rvMyEvents.visibility =
                if (state.events.isNotEmpty()) View.VISIBLE else View.GONE

            binding.tvEmptyState.visibility =
                if (state.events.isEmpty() && !state.isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun observeSideEffect() {
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is MyEventsContract.SideEffect.ShowError -> {
                    binding.tvEmptyState.visibility = View.VISIBLE
                }

                is MyEventsContract.SideEffect.OpenEventDetail -> {

                }
            }
        }
    }
}