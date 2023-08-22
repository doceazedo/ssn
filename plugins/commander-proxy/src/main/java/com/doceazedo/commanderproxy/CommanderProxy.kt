package com.doceazedo.commanderproxy

import com.doceazedo.commanderproxy.events.PluginMessage
import net.md_5.bungee.api.plugin.Plugin


class CommanderProxy : Plugin() {
    companion object {
        lateinit var instance: CommanderProxy
    }

    override fun onEnable() {
        instance = this
        this.proxy.registerChannel("commander:exec");
        this.proxy.pluginManager.registerListener(this, PluginMessage);
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}