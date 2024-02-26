package com.doceazedo.lagosta.events

import com.doceazedo.lagosta.modules.EndCrystalManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
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

        val isServerFull = Bukkit.getServer().onlinePlayers.size >= Bukkit.getServer().maxPlayers
        val isNotSuicide = e.entity.lastDamageCause.cause != EntityDamageEvent.DamageCause.VOID
        val isNotMurder = e.entity.lastDamageCause.entity == e.entity
        if (isServerFull && isNotSuicide && isNotMurder) {
            e.entity.kickPlayer("§eMorrer sozinho enquanto o servidor está lotado\n§ete expulsa para aumentar a rotatividade.\n\n§aVolte quando quiser :)")
        }

        e.deathMessage = "§8${e.deathMessage}"
    }
}