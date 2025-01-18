package com.doceazedo.catraca.events

import com.doceazedo.catraca.managers.IdentityManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object PlayerCommand : Listener {
    private val ALLOW_LIST = listOf("login", "registrar", "register")

    @EventHandler
    fun onPlayerCommand(event: PlayerCommandPreprocessEvent) {
        val command = event.message.split(" ")[0].replace("/", "")

        val isCommandAllowed = ALLOW_LIST.contains(command)
        if (!isCommandAllowed) {
            event.isCancelled = true
            return
        }

        val isLoggedIn = IdentityManager.loggedInUsers.contains(event.player.uniqueId)
        val isRegistering = IdentityManager.registeringUsers.contains(event.player.uniqueId)
        if (isLoggedIn || isRegistering) {
            event.isCancelled = true
            return
        }
    }
}