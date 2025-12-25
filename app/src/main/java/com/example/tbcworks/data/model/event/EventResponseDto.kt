package com.example.tbcworks.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class EventResponseDto(
    val eventId: Int? = null,
    val title: String,
    val organizer: OrganizerDto? = null,
    val category: EventCategory? = null,
    val description: String? = null,
    val agenda: List<AgendaItemDto> = emptyList(),
    val imgUrl: String? = null,
    val userStatus: String,
    val registrationStatus: String,
    val speakers: List<SpeakerDto> = emptyList(),
    val date: DateDto? = null,
    val location: LocationDto? = null,
    val capacity: CapacityDto? = null
)