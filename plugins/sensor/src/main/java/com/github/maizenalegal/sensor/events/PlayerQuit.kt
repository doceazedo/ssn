package com.github.maizenalegal.sensor.events

import com.github.maizenalegal.sensor.Sensor
import com.github.maizenalegal.sensor.identity.Username.logUsernameQuit
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {

    @EventHandler()
    suspend fun onPlayerQuit(e: PlayerQuitEvent) {
        Sensor.instance.launch {
            logUsernameQuit(e.player.displayName)
        }
    }
}