package com.example.tbcworks.presentation.screen.browse_event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemFilterEventBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.screen.home.mapper.day
import com.example.tbcworks.presentation.screen.home.mapper.monthShort
import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.screen.home.mapper.toTimeRange


class EventAdapter(
    private val onItemClick: (EventModel) -> Unit
) : ListAdapter<EventModel, EventAdapter.EventViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old.id == new.id },
        areContentsTheSameCheck = { old, new -> old == new }
    )
) {

    inner class EventViewHolder(private val binding: ItemFilterEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel) {

            // Date info
            binding.tvMonth.text = event.date?.monthShort() ?: "-"
            binding.tvDay.text = event.date?.day() ?: "-"

            // Event info
            binding.tvCategory.text = event.category?.name ?: "-"
            binding.tvStatus.text = event.registrationStatus ?: "-"
            binding.tvTitle.text = event.title ?: "-"
            binding.tvTime.text = event.date?.toTimeRange() ?: "-"
            binding.tvLocation.text = event.location?.venueName ?: "-"

            // Capacity info
            val minParticipants = event.capacity?.minParticipants ?: 0
            val maxCapacity = event.capacity?.maxCapacity ?: 0
            val spotsLeft = (maxCapacity - minParticipants).coerceAtLeast(0)

            binding.tvRegistered.text = "$minParticipants registered"
            binding.tvSpotsLeft.text = "$spotsLeft spots left"

            // Click listener
            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemFilterEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
