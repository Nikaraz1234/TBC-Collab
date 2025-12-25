package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.model.event.EventFilter
import kotlinx.coroutines.flow.Flow

interface EventRepository {
     fun getEvent(eventId: String) : Flow<Resource<Event>>
     fun getEvents(filter: EventFilter?) : Flow<Resource<List<Event>>>
     fun registerOnEvent(eventId: Int): Flow<Resource<Unit>>
     fun cancelRegistrationOnEvent(eventId: Int): Flow<Resource<Unit>>

}