package com.doceazedo.catraca.events

import com.doceazedo.catraca.Catraca.Companion.instance
import com.doceazedo.catraca.Catraca.Companion.waitingRoom
import com.doceazedo.catraca.managers.CaptchaManager
import com.doceazedo.catraca.managers.IdentityManager
import com.github.shynixn.mccoroutine.bukkit.launch
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

        event.player.sendMessage("§aVerificado com sucesso! Por favor, aguarde...")

        if (IdentityManager.registeringUsers.contains(event.player.uniqueId)) {
            instance.launch {
                val (email, password) = IdentityManager.registeringUsers[event.player.uniqueId]!!
                val (identity, error) = IdentityManager.register(email, password, event.player.name)
                if (error?.message != null) {
                    event.player.kickPlayer("§c${error.message}")
                    return@launch
                }
                if (identity == null) return@launch
                event.player.sendMessage("§aRegistrado com sucesso! Por favor, aguarde...")
                IdentityManager.registeringUsers.remove(event.player.uniqueId)
                waitingRoom.enqueuePlayer(event.player)
            }
            return
        }

        if (IdentityManager.loggedInUsers.contains(event.player.uniqueId)) {
            instance.launch {
                event.player.sendMessage("§cTODO: IdentityManager.addUsername()")
            }
            return
        }

        CaptchaManager.captchas.remove(event.player.uniqueId)
        IdentityManager.registeringUsers.remove(event.player.uniqueId)

        event.player.kickPlayer("§4Algo deu errado do nosso lado!\n§cNão encontramos suas credenciais para cadastrar sua nova conta.")
    }
}