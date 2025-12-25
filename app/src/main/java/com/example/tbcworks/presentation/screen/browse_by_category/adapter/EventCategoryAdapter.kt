package com.example.tbcworks.presentation.screen.browse_by_category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemBrowseCategoryBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback
import com.example.tbcworks.presentation.extension.loadImage
import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.screen.home.mapper.toDisplayDate
import com.example.tbcworks.presentation.screen.home.mapper.toTimeRange

class EventCategoryAdapter(
    private val onClick: (EventModel) -> Unit
) : ListAdapter<EventModel, EventCategoryAdapter.EventViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old.id == new.id },
        areContentsTheSameCheck = { old, new -> old == new }
    )
) {

    inner class EventViewHolder(private val binding: ItemBrowseCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel) {
            binding.apply {
                eventTitle.text = event.title
                eventDescription.text = event.description

                val spotsLeftCount = (event.capacity?.maxCapacity?.minus((event.capacity.minParticipants ?: 0)))
                if(spotsLeftCount == 0) {
                    spotsLeft.text = "Full"
                }else{
                    spotsLeft.text = "$spotsLeftCount spots left"
                }

                eventImage.loadImage(event.imgUrl)

                eventDate.text = event.date?.toDisplayDate()
                eventTime.text = event.date?.toTimeRange()
                eventLocation.text = event.location?.venueName

                viewDetailsButton.setOnClickListener { onClick(event) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemBrowseCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}