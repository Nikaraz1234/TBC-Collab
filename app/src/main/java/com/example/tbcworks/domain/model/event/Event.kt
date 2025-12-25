package com.example.tbcworks.domain.model.event

import com.example.tbcworks.data.model.event.EventCategory

data class Event(
    val id: Int,
    val title: String? = null,
    val organizer: Organizer? = null,
    val category: EventCategory? = null,
    val description: String? = null,
    val agenda: List<AgendaItem> = emptyList(),
    val imgUrl: String? = null,
    val userStatus: String? = null,
    val registrationStatus: String? = null,
    val speakers: List<Speaker> = emptyList(),
    val date: EventDate? = null,
    val location: Location? = null,
    val capacity: Capacity? = null
)
