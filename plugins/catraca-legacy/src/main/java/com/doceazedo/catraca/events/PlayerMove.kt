package com.doceazedo.catraca.events

import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

object PlayerMove : Listener {
    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val to: Location = e.from
        to.pitch = e.to.pitch
        to.yaw = e.to.yaw
        e.to = to
    }
}