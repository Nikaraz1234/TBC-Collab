package com.example.tbcworks.domain.usecase.event

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterOnEventUseCase @Inject constructor(
    private val repository: EventRepository
) {

    operator fun invoke(eventId: Int): Flow<Resource<Unit>> {
        return repository.registerOnEvent(eventId)
    }
}
