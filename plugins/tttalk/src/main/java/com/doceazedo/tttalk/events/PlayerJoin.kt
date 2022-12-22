package com.doceazedo.tttalk.events

import com.doceazedo.tttalk.Tttalk
import com.doceazedo.tttalk.utils.IgnoredManager
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.UUID

object PlayerJoin : Listener {
    private val motd = Tttalk.instance.config.getStringList("motd")

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        IgnoredManager.loadIgnoredPlayers(e.player.uniqueId)

        if (motd.isEmpty()) return
        e.player.playSound(e.player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        for (message in motd) {
            e.player.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
        }
    }
}