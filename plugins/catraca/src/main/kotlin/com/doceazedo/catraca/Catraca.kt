package com.doceazedo.catraca

import com.doceazedo.catraca.events.*
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Catraca : JavaPlugin() {
    companion object {
        lateinit var instance: Catraca
    }

    override fun onEnable() {
        instance = this
        instance.saveDefaultConfig()

        Bukkit.getPluginManager().registerEvents(PlayerJoin, this)
        Bukkit.getPluginManager().registerEvents(EntityDamage, this)
        Bukkit.getPluginManager().registerEvents(FoodLevelChange, this)
        Bukkit.getPluginManager().registerEvents(PlayerChat, this)
        Bukkit.getPluginManager().registerEvents(PlayerCommand, this)
        Bukkit.getPluginManager().registerEvents(PlayerInteract, this)
        Bukkit.getPluginManager().registerEvents(PlayerMove, this)
        Bukkit.getPluginManager().registerEvents(PlayerQuit, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
