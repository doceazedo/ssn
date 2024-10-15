package com.doceazedo.catraca.events

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object PlayerCommand : Listener {
    @EventHandler
    fun onPlayerCommand(event: PlayerCommandPreprocessEvent) {
        Bukkit.getLogger().info("Comando: ${event.message}")
        // event.isCancelled = true
    }
}