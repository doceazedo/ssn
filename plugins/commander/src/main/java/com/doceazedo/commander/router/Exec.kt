package com.doceazedo.commander.router

import com.doceazedo.commander.utils.Console
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class ExecRequest(
    val proxy: Boolean = false,
    val command: String,
)

fun Route.execRoute() {
    post("/exec") {
        val request = call.receive<ExecRequest>()
        Console.exec(request.command, request.proxy)
        call.respond(HttpStatusCode.NoContent)
    }
}
