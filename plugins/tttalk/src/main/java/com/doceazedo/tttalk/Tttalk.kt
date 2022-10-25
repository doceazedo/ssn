package com.doceazedo.tttalk

import com.doceazedo.tttalk.commands.MsgCmd
import com.doceazedo.tttalk.events.*
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Tttalk : JavaPlugin() {
    companion object {
        lateinit var instance: Tttalk
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        getCommand("msg").executor = MsgCmd

        Bukkit.getPluginManager().registerEvents(AsyncPlayerChat, this)
        Bukkit.getPluginManager().registerEvents(PlayerDeath, this)
        Bukkit.getPluginManager().registerEvents(PlayerJoin, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}