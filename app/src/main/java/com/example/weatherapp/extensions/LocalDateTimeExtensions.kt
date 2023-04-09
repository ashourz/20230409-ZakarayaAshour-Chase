package com.example.weatherapp.extensions

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Display format for LocalDateTime including conversion from UTC to users current timezone
 * */
fun LocalDateTime.toDisplayFormat(): String {
    val timeZoneOffset = ZonedDateTime.now().offset.totalSeconds.toLong()
    return this.plusSeconds(timeZoneOffset).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
}