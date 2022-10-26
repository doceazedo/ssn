package com.doceazedo.salmos.events

import com.doceazedo.salmos.Salmos
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.event.ProxyPingEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import kotlin.random.Random

object ProxyPing : Listener {
    private val title: String = ChatColor.translateAlternateColorCodes('&', Salmos.config.getString("title"))
    private val messages: List<String> = Salmos.config.getStringList("messages")

    @EventHandler
    fun onProxyPing(e: ProxyPingEvent) {
        val message = getRandomMessage()
        e.response.description = "$title\n§c$message"
    }

    private fun getRandomMessage(): String {
        val i = Random.nextInt(messages.size)
        return ChatColor.translateAlternateColorCodes('&', messages[i])
    }
}