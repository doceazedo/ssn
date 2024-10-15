package com.doceazedo.catraca.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {
    @EventHandler()
    fun onPlayerQuit(e: PlayerQuitEvent) {
        e.quitMessage = ""
    }
}