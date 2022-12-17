package com.github.maizenalegal.sensor

import com.github.maizenalegal.sensor.events.PlayerQuit
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Sensor : JavaPlugin() {
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(PlayerQuit, this)
    }
}