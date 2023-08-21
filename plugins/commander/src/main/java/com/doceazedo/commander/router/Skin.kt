package com.doceazedo.commander.router

import com.doceazedo.commander.utils.Console
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class SkinRequest(
    val player: String,
    val skin: String,
)

fun Route.skinRoute() {
    post("/skin") {
        val request = call.receive<SkinRequest>()
        Console.run("skin set ${request.player} ${request.skin}", true)
        call.respond(HttpStatusCode.NoContent)
    }
}
