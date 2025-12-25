package com.example.tbcworks.domain.usecase.event

import com.example.tbcworks.domain.repository.EventRepository
import javax.inject.Inject

class CancelRegistrationOnEventUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(eventId: Int) = repository.cancelRegistrationOnEvent(eventId)
}