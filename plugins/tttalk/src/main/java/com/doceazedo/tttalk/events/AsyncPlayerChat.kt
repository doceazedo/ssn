package com.doceazedo.tttalk.events

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object AsyncPlayerChat : Listener {
    @EventHandler
    fun onAsyncPlayerChat(e: AsyncPlayerChatEvent) {
        e.isCancelled = true

        val isDonator = e.player.hasPermission("tttalk.donator")
        val isAdmin = e.player.hasPermission("tttalk.admin")

        if (isDonator || isAdmin)
            e.message = ChatColor.translateAlternateColorCodes('&', e.message)

        var colorPrefix = "§f"
        if (isDonator) colorPrefix = "§6"
        if (isAdmin) colorPrefix = "§4"

        val message = "$colorPrefix${e.player.displayName} §9» §7${e.message}"

        for (player in Bukkit.getServer().onlinePlayers) {
            // TODO: check if player doesn't ignore sender
            player.sendMessage(message)
        }
    }
}