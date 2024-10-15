package com.doceazedo.catraca.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage = ""
        // TODO: delete flow
    }
}