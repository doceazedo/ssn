package com.doceazedo.waitingroom.events

import com.doceazedo.waitingroom.managers.QueueManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit: Listener {
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        QueueManager.dequeue(e.player.uniqueId)
    }
}