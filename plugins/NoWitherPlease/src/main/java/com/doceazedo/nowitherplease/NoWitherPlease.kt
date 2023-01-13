package com.doceazedo.nowitherplease

import com.doceazedo.nowitherplease.events.CreatureSpawn
import org.bukkit.plugin.java.JavaPlugin

class NoWitherPlease : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(CreatureSpawn, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}