package com.doceazedo.catraca

import com.doceazedo.catraca.events.*
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import redis.clients.jedis.Jedis

class Catraca : JavaPlugin() {
    companion object {
        lateinit var instance: Catraca
        lateinit var jedis: Jedis
    }

    override fun onEnable() {
        instance = this
        instance.saveDefaultConfig()
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

        val redisHost: String = config.getString("gatekeeper.redis.host")
        val redisPort: Int = config.getInt("gatekeeper.redis.port")
        val redisPassword: String = config.getString("gatekeeper.redis.password")

        jedis = Jedis(redisHost, redisPort)
        jedis.auth(redisPassword)

        Bukkit.getPluginManager().registerSuspendingEvents(PlayerJoin, this)
        Bukkit.getPluginManager().registerEvents(EntityDamage, this)
        Bukkit.getPluginManager().registerEvents(FoodLevelChange, this)
        Bukkit.getPluginManager().registerEvents(PlayerInteract, this)
        Bukkit.getPluginManager().registerEvents(PlayerMove, this)
        Bukkit.getPluginManager().registerEvents(PlayerQuit, this)
    }

    override fun onDisable() {
        jedis.close()
    }
}