package com.doceazedo.commander.router

import com.doceazedo.commander.runnable.TPS
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import java.net.InetAddress
import kotlin.time.Duration
import kotlin.time.measureTime

data class Status(
    val ping: Int,
    val tps: Double,
)

fun Route.statusRoute() {
    get("/status") {
        Bukkit.getLogger().info("GET /status was called")
        val elapsed: Duration = measureTime {
            // Ping the nearest Cloudflare server from us
            InetAddress.getByName("1.1.1.1").isReachable(1000)
        }
        val ping = elapsed.inWholeMilliseconds.toInt()
        val tps = TPS.getTPS()
        val status = Status(ping, tps)
        Bukkit.getLogger().info("$ping ms / $tps tps")
        call.respond(status)
    }
}