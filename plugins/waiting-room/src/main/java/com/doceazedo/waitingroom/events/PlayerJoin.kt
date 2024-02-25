package com.doceazedo.waitingroom.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import kotlin.random.Random

object PlayerJoin: Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val number = Random.nextInt(0, 100)
        e.player.sendMessage("Kotlin test: $number")
        if (e.player.hasPermission("waitingroom.priority")) {
            e.player.sendMessage("§aPriority queue")
        } else {
            e.player.sendMessage("§cRegular queue")
        }
    }
}