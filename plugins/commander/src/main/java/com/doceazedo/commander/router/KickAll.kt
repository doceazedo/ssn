package com.doceazedo.commander.router

import com.doceazedo.commander.Commander
import com.doceazedo.commander.utils.Console
import com.github.shynixn.mccoroutine.bukkit.launch
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit

data class KickAllRequest(
    val reason: String
)

fun Route.kickAll() {
    post("/kick-all") {
        val request = call.receive<KickAllRequest>()
        Commander.instance.launch {
            Bukkit.getServer().onlinePlayers.forEach {
                it.kickPlayer(request.reason)
            }
        }
        call.respond(HttpStatusCode.NoContent)
    }
}