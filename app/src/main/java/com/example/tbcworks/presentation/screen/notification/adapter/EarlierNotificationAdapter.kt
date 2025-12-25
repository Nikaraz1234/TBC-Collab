package com.example.tbcworks.presentation.screen.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemNotificationBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.home.mapper.toRelativeTime
import com.example.tbcworks.presentation.screen.notification.model.NotificationModel

class EarlierNotificationAdapter(
    private val onItemClick: (NotificationModel) -> Unit
) : ListAdapter<NotificationModel, EarlierNotificationAdapter.NotificationViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old.id == new.id },
        areContentsTheSameCheck = { old, new -> old == new }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationModel) {
            binding.tvTitle.text = item.title
            binding.tvTime.text = item.toRelativeTime() // use your mapper function

            // Optionally, show display date for longer format
            // binding.tvTime.text = item.toDisplayDate()
            binding.ivIsNew.isVisible = false
            // Set icon based on type
            binding.ivIcon.setImageResource(
                when (item.type) {
                    com.example.tbcworks.data.model.notification.NotificationType.RegisterConfirmation -> R.drawable.ic_calendar
                    com.example.tbcworks.data.model.notification.NotificationType.DailyReminder -> R.drawable.ic_notifications
                    com.example.tbcworks.data.model.notification.NotificationType.HourReminder -> R.drawable.ic_clock
                    com.example.tbcworks.data.model.notification.NotificationType.WaitlistUpdates -> R.drawable.ic_required2
                    com.example.tbcworks.data.model.notification.NotificationType.EventUpdated -> R.drawable.ic_workshop
                }
            )

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}