package com.doceazedo.tttalk.events

import com.doceazedo.tttalk.utils.IgnoredManager
import com.doceazedo.tttalk.utils.ChatColorManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        // Save player chat data and clear memory
        IgnoredManager.saveIgnoredPlayers(e.player.uniqueId)
        ChatColorManager.saveNameColor(e.player)

        // Show custom quit message
        e.quitMessage = "§8[§c-§8] ${e.player.displayName} saiu"
    }
}