package com.doceazedo.antiburrow

import com.doceazedo.antiburrow.events.PlayerMove
import org.bukkit.plugin.java.JavaPlugin

class AntiBurrow : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerMove, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}