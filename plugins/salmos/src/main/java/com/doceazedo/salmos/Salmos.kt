package com.doceazedo.salmos

import com.doceazedo.salmos.events.ProxyPing
import com.doceazedo.salmos.utils.Config.saveDefaultConfig
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration

class Salmos : Plugin() {
    companion object {
        lateinit var instance: Salmos
        lateinit var config: Configuration
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        proxy.pluginManager.registerListener(this, ProxyPing)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}