package com.doceazedo.lagosta.events

import com.doceazedo.lagosta.utils.Teleport.getRandomLocation
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object PlayerRespawn: Listener {
    @EventHandler
    suspend fun onPlayerRespawn(e: PlayerRespawnEvent) {
        if (e.isBedSpawn) return
        e.respawnLocation = getRandomLocation()
    }
}