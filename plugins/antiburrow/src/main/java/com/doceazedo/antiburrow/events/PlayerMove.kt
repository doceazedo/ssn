package com.doceazedo.antiburrow.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

object PlayerMove : Listener {
    private val allowedBlocks = arrayOf(
        Material.AIR,
        Material.FENCE,
        Material.FENCE_GATE,
        Material.THIN_GLASS,
        Material.STAINED_GLASS_PANE,
    )

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val location = e.player.location
        val block = location.block
        if (block.type in allowedBlocks) return

        val heightThreshold = location.block.y + 0.1
        if (block.type.isOccluding || (block.type.isSolid && location.y < heightThreshold)) {
            e.player.teleport(location.add(0.0, 1.0, 0.0))
        }
    }
}
