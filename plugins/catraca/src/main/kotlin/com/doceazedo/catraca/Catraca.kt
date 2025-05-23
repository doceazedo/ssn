package com.doceazedo.catraca

import com.doceazedo.catraca.commands.LoginCmd
import com.doceazedo.catraca.commands.RegisterCmd
import com.doceazedo.catraca.events.*
import com.doceazedo.catraca.managers.PocketbaseManager
import com.doceazedo.waitingroom.api.WaitingRoomAPI
import com.github.kittinunf.fuel.core.FuelManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import redis.clients.jedis.Jedis

class Catraca : JavaPlugin() {
    companion object {
        lateinit var instance: Catraca
        lateinit var jedis: Jedis
        lateinit var waitingRoom: WaitingRoomAPI

        const val AUTH_FLOW_DURATION = 45L
    }

    override fun onEnable() {
        instance = this
        instance.saveDefaultConfig()

        jedis = Jedis(System.getenv("GK_REDIS_HOST"), System.getenv("GK_REDIS_PORT").toInt())
        jedis.auth(System.getenv("GK_REDIS_PASSWORD"))

        waitingRoom = Bukkit.getServicesManager().load(WaitingRoomAPI::class.java)!!

        FuelManager.instance.forceMethods = true

        PocketbaseManager.loginAsSuperUser()

        Bukkit.getPluginManager().registerEvents(PlayerJoin, this)
        Bukkit.getPluginManager().registerEvents(EntityDamage, this)
        Bukkit.getPluginManager().registerEvents(FoodLevelChange, this)
        Bukkit.getPluginManager().registerEvents(PlayerChat, this)
        Bukkit.getPluginManager().registerEvents(PlayerCommand, this)
        Bukkit.getPluginManager().registerEvents(PlayerInteract, this)
        Bukkit.getPluginManager().registerEvents(PlayerMove, this)
        Bukkit.getPluginManager().registerEvents(PlayerQuit, this)

        getCommand("login")!!.setExecutor(LoginCmd)
        getCommand("registrar")!!.setExecutor(RegisterCmd)
    }

    override fun onDisable() {
        jedis.close()
    }
}
