package com.example.tbcworks.presentation.screen.browse_by_category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentBrowseByCategoryBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.extension.showSnackBar
import com.example.tbcworks.presentation.screen.browse_by_category.adapter.EventCategoryAdapter
import com.example.tbcworks.presentation.screen.home.EventHubFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BrowseByCategoryFragment : BaseFragment<FragmentBrowseByCategoryBinding>(
    FragmentBrowseByCategoryBinding::inflate
) {

    private val viewModel: BrowseByCategoryViewModel by viewModels()
    private val adapter by lazy {
        EventCategoryAdapter { event ->
            val action = BrowseByCategoryFragmentDirections
                .actionBrowseByCategoryFragmentToEventDetailsFragment(event.id.toString())
            findNavController().navigate(action)
        }
    }

    override fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        setFragmentResultListener("categoryRequestKey") { _, bundle ->
            val category = bundle.getString("selectedCategory")
            category?.let {
                binding.tvTitle.text = category
                viewModel.handleIntent(BrowseByCategoryContract.Intent.LoadEvents(it))
            }
        }

        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        collectStateFlow(viewModel.uiState) { state ->
            adapter.submitList(state.events)

            // binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            state.error?.let {
                binding.root.showSnackBar(it)
            }
        }

        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is BrowseByCategoryContract.SideEffect.ShowError -> {
                    binding.root.showSnackBar(effect.message)
                }
            }
        }
    }

}
