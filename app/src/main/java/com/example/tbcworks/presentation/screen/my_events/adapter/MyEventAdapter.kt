package com.example.tbcworks.presentation.screen.my_events.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.databinding.ItemMyEventBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.model.EventModel

class MyEventAdapter(
    private val onItemClick: (EventModel) -> Unit
) : ListAdapter<EventModel, MyEventAdapter.EventViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old.id == new.id },
        areContentsTheSameCheck = { old, new -> old == new }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemMyEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(private val binding: ItemMyEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                onItemClick(item)
            }
        }

        fun bind(event: EventModel) {
            // Time
            val startHour12 = if (event.date!!.startDate.hour == 0 || event.date.startDate.hour == 12) 12 else event.date.startDate.hour % 12
            val startAmPm = if (event.date.startDate.hour < 12) "AM" else "PM"
            binding.tvDate.text = "${startHour12}:${event.date.startDate.minute.toString().padStart(2, '0')}"
            binding.tvAmPm.text = startAmPm

            // Event info
            binding.tvTitle.text = event.title
            binding.tvType.text = event.category?.name // Or any displayable string
            binding.tvLocation.text =
                event.location?.venueName // Assuming LocationModel has a 'name' property
        }
    }
}