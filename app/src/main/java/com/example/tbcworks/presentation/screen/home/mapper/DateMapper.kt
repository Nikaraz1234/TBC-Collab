package com.example.tbcworks.presentation.screen.home.mapper

import com.example.tbcworks.domain.model.event.EventDate
import com.example.tbcworks.presentation.model.EventDateModel
import com.example.tbcworks.presentation.screen.notification.model.NotificationModel
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle
import java.util.Locale

// Month short name: "JAN", "FEB"
fun EventDate.monthShort(): String {
    return startDate.month.name.take(3).uppercase(Locale.getDefault())
}

// Day of month
fun EventDate.day(): String = startDate.dayOfMonth.toString()

// Time range: "08:00 AM - 05:00 PM"
fun EventDate.toTimeRange(): String {
    return "${startDate.toFormattedTime()} - ${endDate.toFormattedTime()}"
}

// Extension to format LocalDateTime to "hh:mm AM/PM"
fun LocalDateTime.toFormattedTime(): String {
    val hour12 = if (hour == 0 || hour == 12) 12 else hour % 12
    val amPm = if (hour < 12) "AM" else "PM"
    val minuteStr = minute.toString().padStart(2, '0')
    return "$hour12:$minuteStr $amPm"
}

fun LocalDateTime.toFormattedDateTime(): String {
    // Short month/day formatting
    val monthShort = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) // e.g., "Dec"
    val dayOfMonth = dayOfMonth // e.g., 25

    // 24-hour time formatting
    val hourStr = hour.toString().padStart(2, '0')
    val minuteStr = minute.toString().padStart(2, '0')

    return "$monthShort, $dayOfMonth $hourStr:$minuteStr"
}

// Optional: formatted register deadline
fun EventDate.registerDeadlineString(): String {
    return if(registerDeadline != null) "${registerDeadline.dayOfMonth} ${registerDeadline.month.name.take(3).uppercase(Locale.getDefault())}, ${registerDeadline.year}, ${registerDeadline.toFormattedTime()}" else return ""
}
// Month short name: "JAN", "FEB"
fun EventDateModel.monthShort(): String {
    return startDate.month.name.take(3).uppercase(Locale.getDefault())
}

// Day of month
fun EventDateModel.day(): String = startDate.dayOfMonth.toString()

// Time range: "08:00 AM - 05:00 PM"
fun EventDateModel.toTimeRange(): String {
    return "${startDate.toFormattedTime()} - ${endDate.toFormattedTime()}"
}

// Optional: formatted register deadline
fun EventDateModel.registerDeadlineString(): String {
    return if (registerDeadline != null) "${registerDeadline.dayOfMonth} ${registerDeadline.month.name.take(3).uppercase(Locale.getDefault())}, ${registerDeadline.year}, ${registerDeadline.toFormattedTime()}" else ""
}

fun EventDate.toDisplayDate(): String {
    val month = startDate.month.name.take(3).capitalize(Locale.getDefault())
    val day = startDate.dayOfMonth
    val year = startDate.year
    return "$month $day, $year"
}

fun EventDateModel.toDisplayDate(): String {
    val month = startDate.month.name.take(3).capitalize(Locale.getDefault())
    val day = startDate.dayOfMonth
    val year = startDate.year
    return "$month $day, $year"
}

fun NotificationModel.toDisplayDate(): String {
    val localDateTime = Instant.parse(createdAt).toLocalDateTime(TimeZone.currentSystemDefault())
    val month = localDateTime.month.name.take(3).capitalize()
    val day = localDateTime.dayOfMonth
    val year = localDateTime.year
    return "$month $day, $year"
}

// Relative time: "15 seconds ago", "5 minutes ago", etc.
fun NotificationModel.toRelativeTime(): String {
    val created = Instant.parse(createdAt)
    val now = Clock.System.now()
    val seconds = (now.toEpochMilliseconds() - created.toEpochMilliseconds()) / 1000

    return when {
        seconds < 60 -> "$seconds seconds ago"
        seconds < 3600 -> "${seconds / 60} minutes ago"
        seconds < 86400 -> "${seconds / 3600} hours ago"
        else -> "${seconds / 86400} days ago"
    }
}