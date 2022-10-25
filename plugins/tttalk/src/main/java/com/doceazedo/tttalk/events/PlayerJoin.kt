package com.doceazedo.tttalk.events

import com.doceazedo.tttalk.Tttalk
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoin : Listener {
    private val motd = Tttalk.instance.config.getStringList("motd")

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (motd.isEmpty()) return
        e.player.playSound(e.player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        for (message in motd) {
            e.player.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
        }
    }
}