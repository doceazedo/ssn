package com.doceazedo.funnydupe.events

import org.bukkit.Bukkit
import org.bukkit.entity.AbstractHorse
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

object EntityDeath : Listener {
    private val rideableMobs = arrayOf(
        EntityType.DONKEY,
        EntityType.LLAMA,
        EntityType.MULE
    )

    @EventHandler
    fun onEntityDeath(e: EntityDeathEvent) {
        if (e.entityType !in rideableMobs) return

        val donkey = e.entity as AbstractHorse
        val donkeyInventory = donkey.inventory.contents

        if (donkeyInventory.isEmpty()) return
        if (donkey.world.time < 18000) return

        e.drops.addAll(donkeyInventory)
        Bukkit.getLogger().info("Duped!")
    }
}