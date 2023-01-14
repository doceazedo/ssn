package com.doceazedo.tttalk.events

import com.doceazedo.tttalk.utils.IgnoredManager
import com.doceazedo.tttalk.utils.ChatColorManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object AsyncPlayerChat : Listener {
    @EventHandler
    fun onAsyncPlayerChat(e: AsyncPlayerChatEvent) {
        e.isCancelled = true
        e.message = ChatColorManager.getColoredMessage(e.message, e.player)

        val nameColor = "§" + ChatColorManager.getNameColor(e.player)
        val message = "$nameColor${e.player.displayName} §9» §7${e.message}"

        for (player in Bukkit.getServer().onlinePlayers) {
            if (IgnoredManager.isIgnoringPlayer(player.uniqueId, e.player.uniqueId)) continue
            player.sendMessage(message)
        }
    }
}