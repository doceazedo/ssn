package com.doceazedo.funnydupe

import com.doceazedo.funnydupe.events.EntityDeath
import org.bukkit.plugin.java.JavaPlugin

class FunnyDupe : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(EntityDeath, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}