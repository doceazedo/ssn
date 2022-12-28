package com.github.maizenalegal.sensor

import com.github.maizenalegal.sensor.commands.PlaytimeCmd
import com.github.maizenalegal.sensor.events.PlayerQuit
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import com.github.shynixn.mccoroutine.bukkit.setSuspendingExecutor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Sensor : JavaPlugin() {
    companion object {
        lateinit var instance: Sensor
    }

    override fun onEnable() {
        instance = this
        getCommand("playtime").setSuspendingExecutor(PlaytimeCmd)
        Bukkit.getPluginManager().registerSuspendingEvents(PlayerQuit, this)
    }
}