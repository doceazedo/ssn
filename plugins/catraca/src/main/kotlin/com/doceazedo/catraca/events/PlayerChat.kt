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

        repeat(30) { event.player.sendMessage("") }
        event.player.sendMessage("§eManeiro, você não é um robô! :)")
        CaptchaManager.captchas.remove(event.player.uniqueId)

        if (IdentityManager.registeringUsers.contains(event.player.uniqueId)) {
            instance.launch {
                event.player.sendMessage("§eRegistrando sua conta, só um momento...")
                val (email, password) = IdentityManager.registeringUsers[event.player.uniqueId]!!
                val (identity, error) = IdentityManager.register(email, password, event.player.name)
                if (error?.message != null) {
                    event.player.kickPlayer("§c${error.message}")
                    return@launch
                }
                if (identity == null) return@launch
                event.player.sendMessage("§aSua conta foi criada com sucesso!")
                IdentityManager.registeringUsers.remove(event.player.uniqueId)
                IdentityManager.loggedInUsers.add(event.player.uniqueId)
                waitingRoom.enqueuePlayer(event.player)
            }
            return
        }

        val authenticatedUser = IdentityManager.authenticatedUsers[event.player.uniqueId]
        if (authenticatedUser != null) {
            val (_, token) = authenticatedUser
            instance.launch {
                event.player.sendMessage("§eRegistrando seu novo usuário, só um momento...")
                val (ok, usernameError) = IdentityManager.addUsername(event.player.name, token)
                if (!ok) {
                    event.player.kickPlayer("§c${usernameError?.message ?: "Algo deu errado!"}")
                    return@launch
                }
                event.player.sendMessage("§aO nome de usuário §e${event.player.displayName} §afoi adicionado à sua conta!")
                IdentityManager.authenticatedUsers.remove(event.player.uniqueId)
                waitingRoom.enqueuePlayer(event.player)
            }
            return
        }

        event.player.kickPlayer("§4Algo deu errado do nosso lado!\n§cNão encontramos suas credenciais para cadastrar sua nova conta.")
    }
}