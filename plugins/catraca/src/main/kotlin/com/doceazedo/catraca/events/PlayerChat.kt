package com.doceazedo.catraca.events

import com.doceazedo.catraca.Catraca.Companion.instance
import com.doceazedo.catraca.Catraca.Companion.waitingRoom
import com.doceazedo.catraca.managers.CaptchaManager
import com.doceazedo.catraca.managers.GatekeeperManager
import com.doceazedo.catraca.managers.IdentityManager
import com.doceazedo.catraca.managers.PocketbaseManager
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
                val (user, registerError) = PocketbaseManager.register(email, password, event.player.name)
                if (user == null) {
                    event.player.kickPlayer(registerError)
                    return@launch
                }
                event.player.sendMessage("§aSua conta foi criada com sucesso!")
                IdentityManager.registeringUsers.remove(event.player.uniqueId)
                IdentityManager.loggedInUsers.add(event.player.uniqueId)
                GatekeeperManager.grantUser(user.id, event.player, true)
                waitingRoom.enqueuePlayer(event.player)
            }
            return
        }

        val isAuthenticated = IdentityManager.authenticatedUsers.contains(event.player.uniqueId)
        if (isAuthenticated) {
            instance.launch {
                val username = PocketbaseManager.findUsername(event.player.name)
                if (username == null) {
                    event.player.kickPlayer("§4Algo deu errado do nosso lado! Esse usuário não foi encontrado.")
                    return@launch
                }

                event.player.sendMessage("§eRegistrando seu novo usuário, só um momento...")
                val newUsername = PocketbaseManager.createUsername(event.player.name, username.owner)
                if (newUsername == null) {
                    event.player.kickPlayer("§cAlgo deu errado! Não foi possível registrar esse usuário.")
                    return@launch
                }
                event.player.sendMessage("§aO nome de usuário §e${event.player.displayName} §afoi adicionado à sua conta!")
                IdentityManager.authenticatedUsers.remove(event.player.uniqueId)
                GatekeeperManager.grantUser(username.owner, event.player, true)
                waitingRoom.enqueuePlayer(event.player)
            }
            return
        }

        event.player.kickPlayer("§4Algo deu errado do nosso lado!\n§cNão encontramos suas credenciais para cadastrar sua nova conta.")
    }
}