package com.doceazedo.commander.router

import com.doceazedo.commander.runnable.TPS
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.net.InetAddress
import kotlin.system.measureTimeMillis

data class StatusResponse(
    val ping: Long,
    val tps: Double,
)

fun Route.statusRoute() {
    get("/status") {
        val ping: Long = measureTimeMillis {
            // Ping UOL DNS server
            InetAddress.getByName("200.221.11.100").isReachable(1000)
        }
        val tps = TPS.getTPS()
        val status = StatusResponse(ping, tps)
        call.respond(status)
    }
}