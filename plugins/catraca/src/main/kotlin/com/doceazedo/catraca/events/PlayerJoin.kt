package com.doceazedo.catraca.events

import com.doceazedo.catraca.Catraca.Companion.instance
import com.doceazedo.catraca.helpers.Messages
import com.doceazedo.catraca.managers.BossBarManager
import com.doceazedo.catraca.managers.IdentityManager
import com.github.shynixn.mccoroutine.bukkit.launch
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.boss.BarColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*

object PlayerJoin : Listener {
    private const val LOGIN_TIME_LIMIT = 45
    private const val MESSAGES_COOLDOWN = 9

    private val spawn = instance.config.get("spawn") as Location

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage = ""
        event.player.teleport(spawn)

        Bukkit.getServer().onlinePlayers.forEach {
            if (it != event.player) {
                event.player.hidePlayer(instance, it)
                it.hidePlayer(instance, event.player)
            }
        }

        // TODO: check for existing grant
        // TODO: create flow

        // TODO: /login
        // TODO: /registrar
        // TODO: captcha response

        val code = UUID.randomUUID().toString().slice(0..4).uppercase()

        instance.launch {
            val identity = IdentityManager.getIdentityFromUsername(event.player.name)
            sendMessages(event.player, identity != null, code)
        }

        instance.launch { showTimer(event.player, code) }
    }

    private suspend fun sendMessages(player: Player, isRegistered: Boolean, code: String) {
        repeat(LOGIN_TIME_LIMIT / MESSAGES_COOLDOWN) {
            if (!player.isOnline) return

            repeat(30) { player.sendMessage("") }
            if (isRegistered) {
                Messages.sendLoginMessages(player, code)
            } else {
                Messages.sendRegisterMessages(player, code)
            }

            delay(MESSAGES_COOLDOWN * 1000L)
        }
        player.kickPlayer("§cVocê demorou mais de §4${LOGIN_TIME_LIMIT}s §cpara fazer login.\nPor favor, tente novamente.")
    }

    private suspend fun showTimer(player: Player, code: String) {
        val bar = BossBarManager.create("§e§lFAÇA LOGIN EM: §b§lssn.gg/login/$code", BarColor.GREEN, player)
        val times = (LOGIN_TIME_LIMIT - 1) * 2
        repeat(times) {
            if (!player.isOnline) return
            bar.progress -= 1.0 / times
            delay(500)
        }
    }
}