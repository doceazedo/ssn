package com.doceazedo.commander.router

import com.doceazedo.commander.Commander
import com.github.shynixn.mccoroutine.bukkit.launch
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit

data class BroadcastRequest(
    val message: String
)

fun Route.broadcast() {
    post("/broadcast") {
        val request = call.receive<BroadcastRequest>()
        Commander.instance.launch {
            Bukkit.getServer().onlinePlayers.forEach {
                it.sendMessage(request.message)
            }
        }
        call.respond(HttpStatusCode.NoContent)
    }
}