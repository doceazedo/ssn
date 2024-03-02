package com.doceazedo.tttalk.utils

import com.doceazedo.tttalk.Tttalk
import org.bukkit.Bukkit
import java.io.File
import java.text.CharacterIterator
import java.text.StringCharacterIterator


object WorldManager {
    val worldDir: File = Bukkit.getWorld("world").worldFolder
    private val worldCreatedAt = Tttalk.instance.config.getLong("world.created")
    val size = humanReadableByteCountSI(directorySize(worldDir.parentFile))
    val age = worldAgeInDays(worldCreatedAt)

    private fun directorySize(dir: File): Long {
        var size: Long = 0
        val files = dir.listFiles() ?: return 0
        for (file in files) {
            size += if (file.isFile) file.length() else directorySize(file)
        }
        return size
    }

    private fun humanReadableByteCountSI(bytes: Long): String {
        var bytes = bytes
        if (-1000 < bytes && bytes < 1000) return "$bytes B"
        val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current())
    }

    private fun worldAgeInDays(timestamp: Long): Long {
        val now = System.currentTimeMillis()
        return (now - timestamp) / (1000 * 60 * 60 * 24)
    }
}