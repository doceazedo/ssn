package com.github.maizenalegal.sensor.events

import com.github.maizenalegal.sensor.Sensor
import com.github.maizenalegal.sensor.managers.PocketbaseManager
import com.github.maizenalegal.sensor.utils.PlayerCount
import com.github.shynixn.mccoroutine.bukkit.launch
import kotlinx.coroutines.delay
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit: Listener {
    @EventHandler
    suspend fun onPlayerQuit(e: PlayerQuitEvent) {
        Sensor.instance.launch {
            PocketbaseManager.logUsernameQuit(e.player.displayName)
            delay(500)
            PlayerCount.update()
        }
    }
}