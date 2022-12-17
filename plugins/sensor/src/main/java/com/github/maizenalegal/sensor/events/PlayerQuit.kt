package com.github.maizenalegal.sensor.events

import com.github.maizenalegal.sensor.enums.Env
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {

    @EventHandler()
    fun onPlayerQuit(e: PlayerQuitEvent) {

        var username = e.player.displayName

        try {
            khttp.post(
                    "${Env.IDENTITY_URL.value}/api/v1/username/$username/quit",
                    mapOf("Authorization" to "Bearer ${Env.IDENTITY_TOKEN.value}")
            )
        } catch (cause: Throwable) {
            Bukkit.getLogger().warning("An error ocurred when trying to fetch /api/v1/username/$username/quit")
            cause.printStackTrace()
        }
    }
}