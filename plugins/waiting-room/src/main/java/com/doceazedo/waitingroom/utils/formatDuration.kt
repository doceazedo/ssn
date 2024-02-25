package com.doceazedo.waitingroom.utils

import kotlin.math.floor

fun formatDuration(seconds: Double): String {
    val minutes = floor(seconds / 60).toInt()
    val remainingSeconds = (seconds % 60).toInt()
    val hours = minutes / 60
    val remainingMinutes = minutes % 60

    val parts = mutableListOf<String>()
    if (hours > 0) parts.add("${hours}h")
    if (remainingMinutes > 0) parts.add("${remainingMinutes}m")
    if (remainingSeconds > 0) parts.add("${remainingSeconds}s")

    if (parts.isEmpty()) return "0s"
    return parts.joinToString(" ")
}