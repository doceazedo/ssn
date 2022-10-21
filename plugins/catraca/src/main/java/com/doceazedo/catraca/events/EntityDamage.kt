package com.doceazedo.catraca.events

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

object EntityDamage: Listener {
    @EventHandler
    fun onEntityDamage(e: EntityDamageEvent) {
        if (e.entity is Player) e.isCancelled = true
    }
}