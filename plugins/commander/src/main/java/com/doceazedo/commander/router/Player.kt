package com.doceazedo.commander.router

import com.doceazedo.commander.Commander
import com.doceazedo.commander.utils.kickPlayer
import com.github.shynixn.mccoroutine.bukkit.launch
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import java.io.File
import java.lang.Error

fun Route.playerRoute() {
    route("/player/{player}") {
        post("/kick") {
            val playerName = call.parameters["player"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            kickPlayer(playerName, "§cEsse usuário foi apagado :(")
            call.respond(HttpStatusCode.NoContent)
        }

        delete {
            val playerName = call.parameters["player"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            kickPlayer(playerName, "§cEsse usuário foi apagado :(")

            Commander.instance.launch {
                val uuid = Bukkit.getOfflinePlayer(playerName).uniqueId.toString()
                listOf("world", "world_nether", "world_the_end").forEach {
                    val world = Bukkit.getWorld(it) ?: return@forEach
                    val filePath = world.worldFolder.absolutePath + "/playerdata/$uuid.dat"
                    val file = File(filePath)
                    try {
                        file.delete()
                        Commander.instance.logger.info("Deleted $filePath")
                    } catch (_: SecurityException) {}
                }
            }

            call.respond(HttpStatusCode.NoContent)
        }
    }
}
