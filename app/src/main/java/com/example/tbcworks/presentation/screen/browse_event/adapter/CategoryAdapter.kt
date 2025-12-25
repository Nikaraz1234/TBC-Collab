package com.example.tbcworks.presentation.screen.browse_event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import com.example.tbcworks.databinding.ItemFilterCategoryBinding
import com.example.tbcworks.presentation.common.GenericDiffCallback

class CategoryAdapter(
    private val onItemClick: (Int) -> Unit
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(
    GenericDiffCallback(
        areItemsTheSameCheck = { old, new -> old == new },
        areContentsTheSameCheck = { old, new -> old == new }
    )
) {

    // Default selection is first item (position 0, usually "All")
    private var selectedPosition = 0

    inner class CategoryViewHolder(private val binding: ItemFilterCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String, position: Int) {
            binding.tvCategory.text = category

            binding.tvCategory.apply {
                setBackgroundResource(
                    if (position == selectedPosition)
                        R.drawable.shape_btn_black_rounded
                    else
                        R.drawable.shape_btn_gray_rounded
                )
                setTextColor(
                    if (position == selectedPosition)
                        context.getColor(R.color.white)
                    else
                        context.getColor(R.color.black)
                )
            }

            // Handle click
            binding.root.setOnClickListener {
                if (selectedPosition != position) {
                    val previousPosition = selectedPosition
                    selectedPosition = position

                    // Refresh old and new selected items
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)

                    // Call the callback with new selection
                    onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemFilterCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    /** Optional: expose currently selected position */
    fun getSelectedPosition(): Int = selectedPosition
}
