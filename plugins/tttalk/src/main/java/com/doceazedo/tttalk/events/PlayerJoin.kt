package com.doceazedo.tttalk.events

import com.doceazedo.tttalk.Tttalk
import com.doceazedo.tttalk.utils.ChatColorManager
import com.doceazedo.tttalk.utils.IgnoredManager
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.FileTime
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.time.Duration
import java.time.Instant


object PlayerJoin : Listener {
    private val serverCreatedAt = Tttalk.instance.config.getString("server.created")
    private val serverVersion = Tttalk.instance.config.getInt("server.version")
    private val world = Bukkit.getWorld("world")
    private val worldFilePath = world.worldFolder.toPath()

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        // Load player chat data into memory
        IgnoredManager.loadIgnoredPlayers(e.player.uniqueId)
        ChatColorManager.loadNameColor(e.player)

        // Show custom join message
        e.joinMessage = "§8[§a+§8] ${e.player.displayName} entrou"

        // Show MOTD
        val worldSize = humanReadableByteCountSI(Files.size(worldFilePath))
        val worldCreationTime = Files.getAttribute(worldFilePath, "creationTime") as FileTime
        val worldAgeDays = worldAgeInDays(worldCreationTime)
        val playerCount = File(worldFilePath.toFile(), "playerdata").listFiles()?.size

        e.player.sendMessage(" ")
        e.player.sendMessage("§aOlá, §e${e.player.displayName}§a!")
        e.player.sendMessage("§e$playerCount §ajogadores já entraram nesse servidor")
        e.player.sendMessage("§aO mundo tem §e$worldAgeDays dias §ade vida e pesa §e$worldSize§a")
        e.player.sendMessage("§aO servidor foi criado em §e$serverCreatedAt §ae está em sua §e${serverVersion}ª §aversão")
        e.player.sendMessage(" ")

        e.player.playSound(e.player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
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

    private fun worldAgeInDays(time: FileTime): Int {
        val timestamp = time.toMillis()
        val then = Instant.ofEpochMilli(timestamp)
        val duration = Duration.between(then, Instant.now())
        return duration.toDaysPart().toInt()
    }
}