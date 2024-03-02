package com.doceazedo.commander.router

import com.doceazedo.commander.Commander
import com.github.shynixn.mccoroutine.bukkit.launch
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import org.bukkit.Sound

data class BroadcastRequest(
    val message: String?,
    val messages: List<String>?,
    val sound: Boolean = false,
)

fun Route.broadcast() {
    post("/broadcast") {
        val request = call.receive<BroadcastRequest>()
        Commander.instance.launch {
            Bukkit.getServer().onlinePlayers.forEach { player ->
                if (request.sound) {
                    player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, .5f, 1f)
                    player.playSound(player.location, Sound.ITEM_GOAT_HORN_SOUND_1, 1f, 1f)
                }

                if (request.messages != null) {
                    request.messages.forEach { message ->
                        if (message.isNotEmpty()) player.sendMessage(message)
                    }
                    return@launch
                }

                if (request.message != null) {
                    player.sendMessage(request.message)
                }
            }
        }
        call.respond(HttpStatusCode.NoContent)
    }
}