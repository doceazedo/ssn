package com.doceazedo.tttalk

import com.doceazedo.tttalk.commands.*
import com.doceazedo.tttalk.events.*
import com.doceazedo.tttalk.utils.IgnoredManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Tttalk : JavaPlugin() {
    companion object {
        lateinit var instance: Tttalk
        var lastChatters = hashMapOf<UUID, UUID>()
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        getCommand("color").executor = ColorCmd
        getCommand("help").executor = HelpCmd
        getCommand("msg").executor = MsgCmd
        getCommand("r").executor = ReplyCmd
        getCommand("ignore").executor = IgnoreCmd

        Bukkit.getPluginManager().registerEvents(AsyncPlayerChat, this)
        Bukkit.getPluginManager().registerEvents(PlayerDeath, this)
        Bukkit.getPluginManager().registerEvents(PlayerJoin, this)
        Bukkit.getPluginManager().registerEvents(PlayerQuit, this)
    }

    override fun onDisable() {
        IgnoredManager.saveAll()
    }
}