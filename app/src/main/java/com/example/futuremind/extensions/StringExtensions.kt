package com.example.futuremind.extensions

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*

/**
 * Extract url from given String
 */
fun String.extractUrl(): String {
    val matcher = Patterns.WEB_URL.matcher(this)
    var url = ""
    while (matcher.find()) {
        url = this.substring(matcher.start(1), matcher.end())
    }
    return url
}

fun String.formatDate(pattern: String): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
    date?.run {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }
    return ""
}