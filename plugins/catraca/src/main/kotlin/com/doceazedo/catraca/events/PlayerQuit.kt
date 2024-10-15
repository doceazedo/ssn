package com.doceazedo.catraca.events

import com.doceazedo.catraca.managers.CaptchaManager
import com.doceazedo.catraca.managers.IdentityManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage = ""
        IdentityManager.loggedInUsers.remove(event.player.uniqueId)
        CaptchaManager.captchas.remove(event.player.uniqueId)
        // TODO: delete flow
    }
}