package com.example.tbcworks.domain.model.event

import com.example.tbcworks.data.model.event.LocationTypeEnum

data class Location(
    val locationType: LocationTypeEnum,
    val venueName: String,
    val address: Address,
    val roomNumber: String,
    val floor: String,
    val additionalNotes: String?
)