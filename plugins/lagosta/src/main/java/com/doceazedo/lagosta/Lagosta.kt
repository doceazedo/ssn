package com.doceazedo.lagosta

import com.doceazedo.lagosta.commands.KillCmd
import com.doceazedo.lagosta.events.*
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Lagosta : JavaPlugin() {
    companion object {
        lateinit var instance: Lagosta
    }

    override fun onEnable() {
        instance = this
        instance.saveDefaultConfig()

        getCommand("kill").executor = KillCmd

        Bukkit.getPluginManager().registerEvents(PlayerInteract, this)
        Bukkit.getPluginManager().registerSuspendingEvents(PlayerJoin, this)
        Bukkit.getPluginManager().registerSuspendingEvents(PlayerRespawn, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}