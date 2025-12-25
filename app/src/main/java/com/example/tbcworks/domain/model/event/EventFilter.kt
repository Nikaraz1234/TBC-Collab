package com.example.tbcworks.domain.model.event

import kotlinx.serialization.Serializable

@Serializable
data class EventFilter(
    val search: String? = null,
    val sortBy: String? = null,
    val sortOrder: String? = null,
    val categories: List<String>? = null,
    val locationTypes: List<String>? = null,
    val dateFrom: String? = null,
    val dateTo: String? = null,
    val capacityAvailability: List<String>? = null,
    val myStatus: String? = null
)