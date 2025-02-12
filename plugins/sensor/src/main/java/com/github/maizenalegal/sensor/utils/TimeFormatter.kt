package com.github.maizenalegal.sensor.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    fun formatDuration(durationInSeconds: Int): String {
        val builder = StringBuilder()
        val days = durationInSeconds / 86400
        val hours = (durationInSeconds % 86400) / 3600
        val minutes = (durationInSeconds % 3600) / 60
        val seconds = durationInSeconds % 60

        if (days > 0) {
            builder.append("$days dia")
            if (days > 1) builder.append("s")
        }
        if (hours > 0) {
            if (builder.isNotEmpty()) builder.append(", ")
            builder.append("$hours hora")
            if (hours > 1) builder.append("s")
        }
        if (minutes > 0) {
            if (builder.isNotEmpty()) builder.append(", ")
            builder.append("$minutes minuto")
            if (minutes > 1) builder.append("s")
        }
        if (seconds > 0) {
            if (builder.isNotEmpty()) builder.append(" e ")
            builder.append("$seconds segundo")
            if (seconds > 1) builder.append("s")
        }

        return builder.toString()
    }

    fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss")
        return dateFormat.format(date)
    }
}