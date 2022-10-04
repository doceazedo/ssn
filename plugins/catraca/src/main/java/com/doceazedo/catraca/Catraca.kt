package com.doceazedo.catraca

import com.doceazedo.catraca.events.PlayerJoin
import com.github.shynixn.mccoroutine.bukkit.launch
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub

class Catraca : JavaPlugin() {
    companion object {
        lateinit var instance: Catraca
        lateinit var jedis: Jedis
    }

    override fun onEnable() {
        instance = this
        instance.saveDefaultConfig()

        val redisHost: String = config.getString("gatekeeper.redis.host")
        val redisPort: Int = config.getInt("gatekeeper.redis.port")
        val redisPassword: String = config.getString("gatekeeper.redis.password")

        jedis = Jedis(redisHost, redisPort)
        jedis.auth(redisPassword)
        jedis["foo"] = "bar"
        val value = jedis["foo"]
        Bukkit.getLogger().info("foo:$value")

        Bukkit.getPluginManager().registerSuspendingEvents(PlayerJoin, this)
    }

    override fun onDisable() {
        jedis.close()
    }
}