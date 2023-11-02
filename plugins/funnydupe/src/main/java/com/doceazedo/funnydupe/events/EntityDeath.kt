package com.doceazedo.funnydupe.events

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.AbstractHorse
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

object EntityDeath : Listener {
    private val rideableMobs = arrayOf(
        EntityType.DONKEY,
        EntityType.LLAMA,
        EntityType.MULE
    )
    private const val distance = 128.0

    @EventHandler
    fun onEntityDeath(e: EntityDeathEvent) {
        if (e.entityType !in rideableMobs) return

        val donkey = e.entity as AbstractHorse
        val donkeyInventory = donkey.inventory.contents
        val world = donkey.world

        if (donkeyInventory.isEmpty()) return
        if (world.time < 18000) return

        val nearbyEntities = donkey.getNearbyEntities(distance, distance, distance)
        val nearbyPlayers = nearbyEntities.filterIsInstance<Player>()
        val nearbyDonkeys = nearbyEntities.filter { it.type in rideableMobs }
        if (nearbyPlayers.size > 1 || nearbyDonkeys.isNotEmpty()) return

        donkeyInventory
            .filterNotNull()
            .filter{ !(it.type == Material.CHEST && it.amount == 1) }
            .forEach{ world.dropItemNaturally(donkey.location, it) }
    }
}