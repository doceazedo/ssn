package com.doceazedo.lagosta.events

import com.doceazedo.lagosta.modules.EndCrystalManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeath : Listener {
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val endCrystalDamagerUUID = EndCrystalManager.crystalEntityDamager[e.entity.uniqueId]
        if (endCrystalDamagerUUID != null) {
            val endCrystalDamager = Bukkit.getServer().getPlayer(endCrystalDamagerUUID)
            e.deathMessage = "§8${e.entity.player.displayName} foi explodido por ${endCrystalDamager.displayName}"
            if (e.entity.uniqueId == endCrystalDamagerUUID) e.deathMessage = "§8${e.entity.player.displayName} se explodiu com um cristal"
            return
        }

        e.deathMessage = "§8${e.deathMessage}"
    }
}