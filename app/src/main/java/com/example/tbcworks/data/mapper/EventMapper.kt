package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.model.event.*
import com.example.tbcworks.domain.model.event.*
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private fun String.toDate(): LocalDateTime =
    Instant.parse(this).toLocalDateTime(TimeZone.currentSystemDefault())

fun EventResponseDto.toDomain(): Event = Event(
    id = eventId ?: 0,
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

fun OrganizerDto.toDomain(): Organizer = Organizer(
    fullName = fullName,
    jobTitle = jobTitle,
    department = department,
    profileImgUrl = profileImgUrl ?: "",
    email = email
)

fun AgendaItemDto.toDomain(): AgendaItem = AgendaItem(
    startTime = startTime,
    duration = duration.orEmpty(),
    title = title.orEmpty(),
    description = description.orEmpty(),
    activityType = activityType.orEmpty(),
    activityLocation = activityLocation.orEmpty()
)


fun SpeakerInfoDto.toDomain(): SpeakerInfo = SpeakerInfo(
    name = name.orEmpty(),
    jobTitle = jobTitle.orEmpty()
)

fun SpeakerDto.toDomain(): Speaker = Speaker(
    fullName = fullName.orEmpty(),
    role = role.orEmpty(),
    description = description.orEmpty(),
)

fun DateDto.toDomain(): EventDate = EventDate(
    eventType = eventType, // EventTypeEnum
    startDate = this.startDate.toLocalDateTime(TimeZone.currentSystemDefault()),
    endDate = this.endDate.toLocalDateTime(TimeZone.currentSystemDefault()),
    registerDeadline = this.registerDeadline?.toLocalDateTime(TimeZone.currentSystemDefault())
)

fun LocationDto.toDomain(): Location = Location(
    locationType = locationType, // LocationTypeEnum
    venueName = venueName,
    address = address.toDomain(),
    roomNumber = roomNumber,
    floor = floor,
    additionalNotes = additionalNotes.orEmpty()
)

fun AddressDto.toDomain(): Address = Address(
    street = street,
    city = city
)

fun CapacityDto.toDomain(): Capacity = Capacity(
    maxCapacity = maxCapacity,
    minParticipants = minParticipants,
    enableWaitlist = enableWaitlist,
    waitlistCapacity = waitlistCapacity,
    autoApprove = autoApprove
)
