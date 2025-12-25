package com.example.tbcworks.domain.usecase.event

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.model.event.EventFilter
import com.example.tbcworks.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyRegisteredEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {

    operator fun invoke(): Flow<Resource<List<Event>>> {
        val filter = EventFilter(
            myStatus = "Registered"
        )

        return repository.getEvents(filter)
            .map { resource ->
                resource
            }
    }
}
