package com.doceazedo.catraca.events

import com.doceazedo.catraca.managers.CaptchaManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object PlayerChat : Listener {
    @EventHandler
    fun onPlayerChat (event: AsyncPlayerChatEvent) {
        event.isCancelled = true

        val captcha = CaptchaManager.captchas[event.player.uniqueId] ?: return
        val guess = event.message.trim().lowercase()
        if (!captcha.answers.contains(guess)) {
            event.player.sendMessage("§cA resposta \"§4$guess\" §cestá incorreta, tente novamente.")
            return
        }


    }
}