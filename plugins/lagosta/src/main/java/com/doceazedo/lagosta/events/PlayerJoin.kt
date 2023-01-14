package com.doceazedo.lagosta.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import com.doceazedo.lagosta.utils.Teleport.teleportToRandomLocation

object PlayerJoin: Listener {
    @EventHandler
    suspend fun onPlayerJoin(e: PlayerJoinEvent) {
        if (e.player.hasPlayedBefore()) return
        teleportToRandomLocation(e.player)
    }
}