package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.event.EventResponseDto
import com.example.tbcworks.domain.model.event.EventFilter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {
    @GET("events")
    suspend fun getEvents(
        @Query("Search") search: String? = null,
        @Query("Categories") categories: List<String>? = null,
        @Query("Locationtypes") locationTypes: List<String>? = null,
        @Query("DateFrom") dateFrom: String? = null,
        @Query("DateTo") dateTo: String? = null,
        @Query("CapacityAvailability") capacityAvailability: List<String>? = null,
        @Query("MyStatus") myStatus: String? = null
    ): List<EventResponseDto>

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") eventId: String
    ): EventResponseDto

    @POST("events/{eventId}/registration/register")
    suspend fun registerOnEvent(
        @Path("eventId") eventId: Int
    ): Response<Unit>

    @POST("events/{eventId}/registration/cancel")
    suspend fun cancelRegistrationOnEvent(
        @Path("eventId") eventId: Int
    ): Response<Unit>
}