package com.example.tbcworks.presentation.mapper

import com.example.tbcworks.domain.model.event.Address
import com.example.tbcworks.domain.model.event.AgendaItem
import com.example.tbcworks.domain.model.event.Capacity
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.model.event.EventDate
import com.example.tbcworks.domain.model.event.Location
import com.example.tbcworks.domain.model.event.Organizer
import com.example.tbcworks.domain.model.event.Speaker
import com.example.tbcworks.domain.model.event.SpeakerInfo
import com.example.tbcworks.presentation.model.AddressModel
import com.example.tbcworks.presentation.model.AgendaModel
import com.example.tbcworks.presentation.model.CapacityModel
import com.example.tbcworks.presentation.model.EventDateModel
import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.model.LocationModel
import com.example.tbcworks.presentation.model.OrganizerModel
import com.example.tbcworks.presentation.model.SpeakerInfoModel
import com.example.tbcworks.presentation.model.SpeakerModel
import kotlin.math.min

fun Event.toPresentation(): EventModel = EventModel(
    id = id,
    title = title,
    organizer = organizer?.toPresentation(),
    category = category,
    description = description,
    agenda = agenda.map { it.toPresentation() },
    imgUrl = imgUrl,
    userStatus = userStatus,
    registrationStatus = registrationStatus,
    speakers = speakers.map { it.toPresentation() },
    date = date?.toPresentation(),
    location = location?.toPresentation(),
    capacity = capacity?.toPresentation()
)

fun EventModel.toDomain(): Event = Event(
    id = id,
    title = title,
    organizer = organizer?.toDomain(),
    category = category,
    description = description,
    agenda = agenda.map { it.toDomain() },
    imgUrl = imgUrl,
    userStatus = userStatus,
    registrationStatus = registrationStatus,
    speakers = speakers.map { it.toDomain() },
    date = date?.toDomain(),
    location = location?.toDomain(),
    capacity = capacity?.toDomain()
)


// Address
fun Address.toPresentation(): AddressModel = AddressModel(
    street = street,
    city = city
)

// AgendaItem
fun AgendaItem.toPresentation(): AgendaModel = AgendaModel(
    startTime = startTime,
    duration = duration,
    title = title,
    description = description,
    activityType = activityType,
    activityLocation = activityLocation
)

// Capacity
fun Capacity.toPresentation(): CapacityModel = CapacityModel(
    maxCapacity = maxCapacity,
    minParticipants = minParticipants,
    enableWaitlist = enableWaitlist,
    waitlistCapacity = waitlistCapacity,
    autoApprove = autoApprove
)

// EventDate
fun EventDate.toPresentation(): EventDateModel = EventDateModel(
    eventType = eventType,
    startDate = startDate,
    endDate = endDate,
    registerDeadline = registerDeadline
)

// Location
fun Location.toPresentation(): LocationModel = LocationModel(
    locationType = locationType,
    venueName = venueName,
    address = address.toPresentation(),
    roomNumber = roomNumber,
    floor = floor,
    additionalNotes = additionalNotes
)

// Organizer
fun Organizer.toPresentation(): OrganizerModel = OrganizerModel(
    fullName = fullName,
    jobTitle = jobTitle,
    department = department,
    profileImgUrl = profileImgUrl,
    email = email
)

// Speaker
fun Speaker.toPresentation(): SpeakerModel = SpeakerModel(
    fullName = fullName,
    role = role,
    description = description,
)

// SpeakerInfo
fun SpeakerInfo.toPresentation(): SpeakerInfoModel = SpeakerInfoModel(
    name = name,
    jobTitle = jobTitle
)

// Address
fun AddressModel.toDomain(): Address = Address(
    street = street,
    city = city
)

// AgendaItem
fun AgendaModel.toDomain(): AgendaItem = AgendaItem(
    startTime = startTime,
    duration = duration,
    title = title,
    description = description,
    activityType = activityType,
    activityLocation = activityLocation
)

// Capacity
fun CapacityModel.toDomain(): Capacity = Capacity(
    maxCapacity = maxCapacity,
    minParticipants = minParticipants,
    enableWaitlist = enableWaitlist,
    waitlistCapacity = waitlistCapacity,
    autoApprove = autoApprove
)

// EventDate
fun EventDateModel.toDomain(): EventDate = EventDate(
    eventType = eventType,
    startDate = startDate,
    endDate = endDate,
    registerDeadline = registerDeadline
)

// Location
fun LocationModel.toDomain(): Location = Location(
    locationType = locationType,
    venueName = venueName,
    address = address.toDomain(),
    roomNumber = roomNumber,
    floor = floor,
    additionalNotes = additionalNotes
)

// Organizer
fun OrganizerModel.toDomain(): Organizer = Organizer(
    fullName = fullName,
    jobTitle = jobTitle ?: "",
    department = department,
    profileImgUrl = profileImgUrl,
    email = email
)

// Speaker
fun SpeakerModel.toDomain(): Speaker = Speaker(
    fullName = fullName,
    role = role,
    description = description,
)

// SpeakerInfo
fun SpeakerInfoModel.toDomain(): SpeakerInfo = SpeakerInfo(
    name = name,
    jobTitle = jobTitle
)
