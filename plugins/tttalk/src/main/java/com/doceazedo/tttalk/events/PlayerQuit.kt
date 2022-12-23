package com.doceazedo.tttalk.events

import com.doceazedo.tttalk.utils.IgnoredManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        // Save who this player ignores // TODO: clear memory
        IgnoredManager.saveIgnoredPlayers(e.player.uniqueId)

        // Show custom quit message
        e.quitMessage = "§8[§c-§8] ${e.player.displayName} saiu"
    }
}