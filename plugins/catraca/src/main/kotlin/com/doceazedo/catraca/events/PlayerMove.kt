package com.doceazedo.catraca.events

import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

object PlayerMove : Listener {
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val to: Location = event.from
        to.pitch = event.to.pitch
        to.yaw = event.to.yaw
        event.to = to
    }
}