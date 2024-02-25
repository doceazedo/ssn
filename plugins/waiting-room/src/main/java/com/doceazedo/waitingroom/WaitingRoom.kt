package com.doceazedo.waitingroom

import com.doceazedo.waitingroom.api.WaitingRoomAPI
import com.doceazedo.waitingroom.enums.Env
import com.doceazedo.waitingroom.events.PlayerQuit
import com.doceazedo.waitingroom.managers.QueueManager
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import redis.clients.jedis.Jedis


class WaitingRoom : JavaPlugin() {
    companion object {
        lateinit var intance: WaitingRoom
        lateinit var jedis: Jedis
    }

    override fun onEnable() {
        intance = this
        jedis = Jedis(Env.REDIS_HOST.value, Env.REDIS_PORT.value.toInt())
        jedis.auth(Env.REDIS_PASSWORD.value)

        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        Bukkit.getPluginManager().registerEvents(PlayerQuit, this)

        val scheduler = server.scheduler
        scheduler.scheduleSyncRepeatingTask(this, {
            launch {
                QueueManager.runQueue()
            }
        }, 0L, 20L * 2)
    }

    override fun onLoad() {
        Bukkit.getLogger().info("Registering WaitingRoom API")
        Bukkit.getServicesManager().register(WaitingRoomAPI::class.java, WaitingRoomAPI, this, ServicePriority.Normal)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}