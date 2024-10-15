package com.doceazedo.catraca.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object PlayerInteract : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        event.isCancelled = true
    }
}