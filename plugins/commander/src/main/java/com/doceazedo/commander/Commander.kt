package com.doceazedo.commander

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.plugin.java.JavaPlugin

class Commander : JavaPlugin() {
    override fun onEnable() {
        embeddedServer(Netty, port = 25573) {
            routing {
                get("/") {
                    logger.info("Commander: GET /")
                    call.respondText("Hello, world!")
                }
            }
        }.start(wait = true)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}