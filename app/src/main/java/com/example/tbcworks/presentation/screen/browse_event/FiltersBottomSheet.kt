package com.example.tbcworks.presentation.screen.browse_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcworks.databinding.FragmentFiltersBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersBottomSheet(
    private val onFilterSelected: (online: Boolean, offline: Boolean, available: Boolean, full: Boolean) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentFiltersBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnApplyFilters.setOnClickListener {
            val online = binding.cbOnline.isChecked
            val offline = binding.cbOffline.isChecked
            val available = binding.cbAvailable.isChecked
            val full = binding.cbFull.isChecked
            onFilterSelected(online, offline, available, full)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
