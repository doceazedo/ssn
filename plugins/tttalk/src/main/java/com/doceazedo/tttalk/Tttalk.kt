package com.doceazedo.tttalk

import com.doceazedo.tttalk.commands.MsgCmd
import com.doceazedo.tttalk.commands.ReplyCmd
import com.doceazedo.tttalk.events.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.collections.HashMap

class Tttalk : JavaPlugin() {
    companion object {
        lateinit var instance: Tttalk
        var lastChatters: HashMap<UUID, UUID> = HashMap<UUID, UUID>()
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        getCommand("msg").executor = MsgCmd
        getCommand("r").executor = ReplyCmd

        Bukkit.getPluginManager().registerEvents(AsyncPlayerChat, this)
        Bukkit.getPluginManager().registerEvents(PlayerDeath, this)
        Bukkit.getPluginManager().registerEvents(PlayerJoin, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}