package com.example.foodylearn.util.extensions


import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun DateFormat.getSeparator(date: Date? = null) = format(date ?: Date()).first { !it.isDigit() }

fun String.asIso8061(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return format.parse(this)
}

fun Date?.shortFormat(): String {
    if (this == null) return ""
    val separator = DateFormat.getDateInstance(
        DateFormat.SHORT,
        Locale.getDefault(),
    ).getSeparator()

    return SimpleDateFormat(
        "yyyy${separator}MM${separator}dd",
        Locale.getDefault(),
    ).format(this)
}

fun Date.formatIso8061(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return format.format(this)
}

fun parseDateToIso8061(dateStr: String?): Date? {
    return try {
        dateStr?.asIso8061()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun isoOffsetDateTime(year: Int, month: Int, day: Int): String {
    val iso_month = if (month < 10) "0$month" else month.toString()
    val iso_day = if (day < 10) "0$day" else day.toString()
    return buildString {
        append(year)
        append("-")
        append(iso_month)
        append("-")
        append(iso_day)
        append("T00:00:00")
        append(getTimeZoneStr())
    }
}

fun getTimeZoneStr(): String {
    val zone = TimeZone.getDefault()
    return zone.getDisplayName(false, TimeZone.SHORT).substringAfter("GMT")
}

fun DateFormat.addLeadingZeros(date: Date, separator: Char): String {
    val dateStr = format(date)
    val dateWithLeadingZeros = StringBuilder()
    var lastFoundedSeparatorIndex = 0
    for (i in dateStr.indices) {
        if (dateStr[i] == separator || i == dateStr.length - 1) {
            val datePiece = dateStr.substring(lastFoundedSeparatorIndex, i + 1)
            lastFoundedSeparatorIndex = i + 1
            val digitCount = datePiece.count { it.isDigit() }
            if (digitCount == 1) {
                dateWithLeadingZeros.append("0")
            }
            dateWithLeadingZeros.append(datePiece)
        }
    }
    return dateWithLeadingZeros.toString()
}

val Calendar.year: Int
    get() = get(Calendar.YEAR)

val Calendar.day: Int
    get() = get(Calendar.DAY_OF_MONTH)

val Calendar.month: Int
    get() = get(Calendar.MONTH) + 1
