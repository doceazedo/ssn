package com.github.maizenalegal.sensor.events

import com.github.maizenalegal.sensor.Sensor
import com.github.maizenalegal.sensor.identity.Username.logUsernameJoin
import com.github.maizenalegal.sensor.utils.PlayerCount
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoin: Listener {
    @EventHandler
    suspend fun onPlayerJoin(e: PlayerJoinEvent) {
        Sensor.instance.launch {
            logUsernameJoin(e.player.displayName)
            PlayerCount.update()
        }
    }
}