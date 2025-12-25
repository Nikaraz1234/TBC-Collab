package com.example.tbcworks.data.repository


import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.service.EventService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.model.event.EventFilter
import com.example.tbcworks.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val service: EventService,
    private val handleResponse: HandleResponse
) : EventRepository {

    override fun getEvents(filter: EventFilter?): Flow<Resource<List<Event>>> = flow {
        emitAll(
            handleResponse.safeApiCall {
                service.getEvents(
                    search = filter?.search,
                    categories = filter?.categories,
                    locationTypes = filter?.locationTypes,
                    dateFrom = filter?.dateFrom,
                    dateTo = filter?.dateTo,
                    capacityAvailability = filter?.capacityAvailability,
                    myStatus = filter?.myStatus
                )
            }.map { resource ->
                resource.asResource { list ->
                    list.map { it.toDomain() }
                }
            }
        )
    }

    override fun getEvent(eventId: String): Flow<Resource<Event>> = flow {
        emitAll(
            handleResponse.safeApiCall {
                service.getEventById(eventId)
            }.map { it.asResource { dto -> dto.toDomain() } }
        )
    }

    override fun registerOnEvent(eventId: Int): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            service.registerOnEvent(eventId)
        }
    }
    override fun cancelRegistrationOnEvent(eventId: Int): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            service.cancelRegistrationOnEvent(eventId)
        }
    }

}