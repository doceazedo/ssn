package com.doceazedo.tttalk.events

import com.doceazedo.tttalk.utils.IgnoredManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        IgnoredManager.saveIgnoredPlayers(e.player.uniqueId)
    }
}