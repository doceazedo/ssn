package com.doceazedo.commander.router

import com.doceazedo.commander.utils.Console
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class GiveRankRequest(
    val player: String,
    val rank: String,
    val duration: String,
)

fun Route.rankRoute() {
    post("/give-rank") {
        val request = call.receive<GiveRankRequest>()
        Console.exec("lp user ${request.player} parent addtemp ${request.rank} ${request.duration} accumulate")
        call.respond(HttpStatusCode.NoContent)
    }
}