package com.doceazedo.waitingroom

import com.doceazedo.waitingroom.events.PlayerJoin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class WaitingRoom : JavaPlugin() {
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(PlayerJoin, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}