package com.doceazedo.lagosta.events

import com.doceazedo.lagosta.modules.EndCrystalManager
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause.*

object EntityDamageByEntity : Listener {
    @EventHandler
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        val endCrystalDamager = getEndCrystalDamager(e)
        if (endCrystalDamager != null) {
            EndCrystalManager.crystalEntityDamager[e.entity.uniqueId] = endCrystalDamager.uniqueId
            return
        }

        EndCrystalManager.crystalEntityDamager.remove(e.entity.uniqueId)
    }

    private fun getEndCrystalDamager(e: EntityDamageByEntityEvent): Player? {
        if (e.entity !is Player || e.damager !is EnderCrystal) return null

        val crystalLastDamageCause = e.damager.lastDamageCause
        if (crystalLastDamageCause !is EntityDamageByEntityEvent) return null
        if (crystalLastDamageCause.damager !is Player) return null
        if (crystalLastDamageCause.cause != ENTITY_ATTACK && crystalLastDamageCause.cause != ENTITY_SWEEP_ATTACK) return null

        return crystalLastDamageCause.damager as Player
    }
}