package com.doceazedo.lagosta.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object PlayerInteract : Listener {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        if (e.action != Action.RIGHT_CLICK_BLOCK || e.hand != EquipmentSlot.HAND) return
        if (e.clickedBlock.type != Material.BED_BLOCK) return
        e.player.bedSpawnLocation = e.clickedBlock.location
        e.player.sendMessage("Você renascerá aqui a menos que essa cama seja destruída.")
    }
}