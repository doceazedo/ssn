package com.github.maizenalegal.sensor.events

import com.github.maizenalegal.sensor.Sensor
import com.github.maizenalegal.sensor.managers.PocketbaseManager
import com.github.maizenalegal.sensor.utils.PlayerCount
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoin: Listener {
    @EventHandler
    suspend fun onPlayerJoin(e: PlayerJoinEvent) {
        e.player.noDamageTicks = 0
        Sensor.instance.launch {
            PocketbaseManager.logUsernameJoin(e.player.displayName)
            PlayerCount.update()
        }
    }
}