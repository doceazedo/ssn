package com.doceazedo.antiburrow.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

object PlayerMove : Listener {
    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val location = e.player.location
        val blockType = location.world.getBlockAt(location.blockX, location.blockY, location.blockZ).type
        if (blockType == Material.AIR || !blockType.isOccluding) return
        e.player.teleport(location.add(0.0, 1.0, 0.0))
    }
}
