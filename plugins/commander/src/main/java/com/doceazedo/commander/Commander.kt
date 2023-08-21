package com.doceazedo.commander

import com.doceazedo.commander.router.*
import com.doceazedo.commander.runnable.TPS
import com.github.shynixn.mccoroutine.bukkit.launch
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.text.DateFormat


class Commander : JavaPlugin() {
    companion object {
        lateinit var instance: Commander
    }

    override fun onEnable() {
        instance = this

        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, TPS, 100L, 1L)

        launch {
            withContext(Dispatchers.IO) {
                embeddedServer(Netty, port = 25574) {
                    install(ContentNegotiation) {
                        gson {
                            setDateFormat(DateFormat.LONG)
                            setPrettyPrinting()
                        }
                    }
                    routing {
                        get("/") {
                            logger.info("Commander: GET /")
                            call.respondText("Hello, world!")
                        }

                        route("/api/public") {
                            statusRoute()
                        }

                        route("/api/private") {
                            skinRoute()
                            execRoute()
                        }
                    }
                }.start(wait = true)
            }
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}