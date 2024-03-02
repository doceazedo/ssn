package com.doceazedo.inflightconn.events

import com.doceazedo.inflightconn.InFlightConn
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

object PlayerLogin: Listener {
    private val limit = InFlightConn.config.getInt("limit")

    @EventHandler
    suspend fun onPlayerLogin(e: PostLoginEvent) {
        val connections = ProxyServer.getInstance().players.filter {
            val ip = e.player.address.address.hostAddress
            return@filter it.address.address.hostAddress == ip
        }
        if (connections.size > limit) {
            e.player.disconnect("§cEsse IP já está conectado no servidor.")
        }
    }
}