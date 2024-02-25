package com.github.maizenalegal.sensor

import com.github.maizenalegal.sensor.commands.PlaytimeCmd
import com.github.maizenalegal.sensor.enums.Env
import com.github.maizenalegal.sensor.events.PlayerJoin
import com.github.maizenalegal.sensor.events.PlayerQuit
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

    override fun onEnable() {
        instance = this
        jedis = Jedis(Env.REDIS_HOST.value, Env.REDIS_PORT.value.toInt())
        jedis.auth(Env.REDIS_PASSWORD.value)
        launch {
            PlayerCount.update()
            PlayerCount.updateMax()
        }
        getCommand("playtime").setSuspendingExecutor(PlaytimeCmd)
        Bukkit.getPluginManager().registerSuspendingEvents(PlayerJoin, this)
        Bukkit.getPluginManager().registerSuspendingEvents(PlayerQuit, this)
    }
}