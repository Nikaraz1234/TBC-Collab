package com.example.tbcworks.presentation.model

import com.example.tbcworks.data.model.event.EventCategory

data class EventModel(
    val id: Int,
    val title: String? = null,
    val organizer: OrganizerModel? = null,
    val category: EventCategory? = null,
    val description: String? = null,
    val agenda: List<AgendaModel> = emptyList(),
    val imgUrl: String? = null,
    val userStatus: String? = null,
    val registrationStatus: String? = null,
    val speakers: List<SpeakerModel> = emptyList(),
    val date: EventDateModel? = null,
    val location: LocationModel? = null,
    val capacity: CapacityModel? = null
)