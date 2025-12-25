package com.example.tbcworks.presentation.screen.notification

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.FragmentNotificationBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screen.notification.adapter.EarlierNotificationAdapter
import com.example.tbcworks.presentation.screen.notification.adapter.NewNotificationAdapter
import com.example.tbcworks.presentation.screen.notification.model.NotificationModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding::inflate
) {

    private val viewModel: NotificationViewModel by viewModels()

    // Lazy adapters
    private val newAdapter by lazy {
        NewNotificationAdapter { notification ->
            showNotificationBottomSheet(notification)
            viewModel.onIntent(NotificationContract.Intent.NotificationClicked(notification))
        }
    }

    private val earlierAdapter by lazy {
        EarlierNotificationAdapter { notification ->
            showNotificationBottomSheet(notification)
            viewModel.onIntent(NotificationContract.Intent.NotificationClicked(notification))
        }
    }

    override fun bind() {
        setupRecyclerViews()
        observeState()
        loadNotifications()
    }

    private fun setupRecyclerViews() {
        binding.rvNewNotification.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newAdapter
        }

        binding.rvEarlierNotification.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = earlierAdapter
        }
    }

    private fun showNotificationBottomSheet(notification: NotificationModel) {
        val bottomSheet = NotificationDetailBottomSheet(
            title = notification.title ?: "No Title",
            dateTime = notification.createdAt ?: "",
            location = notification.message ?: ""
        )
        bottomSheet.show(parentFragmentManager, "NotificationBottomSheet")
    }

    private fun observeState() {
        collectStateFlow(viewModel.uiState) { state ->

            // Remove duplicates if needed
            val uniqueNotifications = state.notifications.distinctBy { it.id }

            // Split into new vs earlier based on isRead
            val newNotifications = uniqueNotifications.filter { !it.isRead }       // unread
            val earlierNotifications = uniqueNotifications.filter { it.isRead }   // read

            // Update adapters
            newAdapter.submitList(newNotifications)
            earlierAdapter.submitList(earlierNotifications)

            // Handle empty views
            binding.tvEmptyNew.visibility = if (newNotifications.isEmpty()) View.VISIBLE else View.GONE
            binding.tvEmptyEarlier.visibility = if (earlierNotifications.isEmpty()) View.VISIBLE else View.GONE
        }

        collectFlow(viewModel.sideEffect) { sideEffect ->
            when (sideEffect) {
                is NotificationContract.SideEffect.ShowError -> {
                    // Show toast or snackbar
                }
                is NotificationContract.SideEffect.OpenNotification -> {
                    // Handle notification click
                }
            }
        }
    }

    private fun loadNotifications() {
        viewModel.onIntent(NotificationContract.Intent.LoadNotifications)
    }
}

