package com.potato.antiburrow

import com.potato.antiburrow.listener.AntiBurrowListener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()
        server.pluginManager.registerEvents(AntiBurrowListener(this), this)
    }
}