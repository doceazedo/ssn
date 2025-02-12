package com.github.maizenalegal.sensor

import com.github.kittinunf.fuel.core.FuelManager
import com.github.maizenalegal.sensor.commands.PlaytimeCmd
import com.github.maizenalegal.sensor.events.PlayerJoin
import com.github.maizenalegal.sensor.events.PlayerQuit
import com.github.maizenalegal.sensor.managers.PocketbaseManager
import com.github.maizenalegal.sensor.utils.PlayerCount
import com.github.shynixn.mccoroutine.bukkit.launch
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import com.github.shynixn.mccoroutine.bukkit.setSuspendingExecutor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import redis.clients.jedis.Jedis

class Sensor : JavaPlugin() {
    companion object {
        lateinit var instance: Sensor
        lateinit var jedis: Jedis
    }

    private val REDIS_HOST = System.getenv("GK_REDIS_HOST")
    private val REDIS_PORT = System.getenv("GK_REDIS_PORT")
    private val REDIS_PASSWORD = System.getenv("GK_REDIS_PASSWORD")

    override fun onEnable() {
        instance = this

        jedis = Jedis(REDIS_HOST, REDIS_PORT.toInt())
        jedis.auth(REDIS_PASSWORD)

        FuelManager.instance.forceMethods = true
        PocketbaseManager.loginAsSuperUser()

        launch {
            PlayerCount.update()
            PlayerCount.updateMax()
        }

        getCommand("playtime").setSuspendingExecutor(PlaytimeCmd)
        Bukkit.getPluginManager().registerSuspendingEvents(PlayerJoin, this)
        Bukkit.getPluginManager().registerSuspendingEvents(PlayerQuit, this)
    }
}