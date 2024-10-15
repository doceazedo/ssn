package com.doceazedo.catraca.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object PlayerChat : Listener {
    @EventHandler
    fun onPlayerChat (event: AsyncPlayerChatEvent) {
        event.isCancelled = true
    }
}