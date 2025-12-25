package com.example.tbcworks.presentation.model

import com.example.tbcworks.data.model.event.LocationTypeEnum
import com.example.tbcworks.domain.model.event.Address

data class LocationModel(
    val locationType: LocationTypeEnum,
    val venueName: String,
    val address: AddressModel,
    val roomNumber: String,
    val floor: String,
    val additionalNotes: String?
)