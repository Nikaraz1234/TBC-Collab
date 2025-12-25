package com.example.tbcworks.presentation.screen.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcworks.databinding.BottomSheetNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationDetailBottomSheet(
    private val title: String,
    private val dateTime: String,
    private val location: String
) : BottomSheetDialogFragment() {


    private var _binding: BottomSheetNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNotificationTitle.text = title
        binding.tvDateTime.text = dateTime
        binding.tvLocation.text = location

        binding.btnSendQuestion.setOnClickListener {
            // handle send question
        }
        binding.btnAddCalendar.setOnClickListener {
            // handle add to calendar
        }
        binding.btnCancelRegistration.setOnClickListener { dismiss() }
        binding.tvClose.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}