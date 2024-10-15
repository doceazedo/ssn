package com.doceazedo.catraca.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object PlayerCommand: Listener {
    @EventHandler
    fun onPlayerCommand(e: PlayerCommandPreprocessEvent) {
        e.isCancelled = true
    }
}