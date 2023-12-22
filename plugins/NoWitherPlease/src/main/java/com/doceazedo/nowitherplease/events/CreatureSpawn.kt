package com.doceazedo.nowitherplease.events

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent

object CreatureSpawn : Listener {
    @EventHandler
    fun onCreatureSpawn(e: CreatureSpawnEvent) {
        if (e.entityType != EntityType.WITHER) return
        val nearbyEntities = e.location.world.getNearbyEntities(e.location, 32.0, 255.0, 32.0)
        val nearbyWithers = nearbyEntities.filter { it.type == EntityType.WITHER }
        if (nearbyWithers.size > 3) e.isCancelled = true
    }
}