package com.example.weatherapp.extensions

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun LocalDateTime.toDisplayFormat(): String {
    val timeZoneOffset = ZonedDateTime.now().offset.totalSeconds.toLong()
    return this.plusSeconds(timeZoneOffset).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
}