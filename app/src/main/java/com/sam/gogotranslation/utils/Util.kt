package com.sam.gogotranslation.utils

import android.text.format.DateFormat.getBestDateTimePattern
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Date.toZonedDateTime(zone: ZoneId = ZoneId.systemDefault()): ZonedDateTime? {
    runCatching {
        return ZonedDateTime.ofInstant(this.toInstant(), zone)
    }.getOrElse {
        Timber.e(it)
        return null
    }
}

fun ZonedDateTime.format(skeleton: String): String = run {
    runCatching {
        format(
            DateTimeFormatter.ofPattern(
                getBestDateTimePattern(Locale.getDefault(), skeleton)
            )
        )
    }.getOrElse {
        Timber.d(it)
        ""
    }
}
