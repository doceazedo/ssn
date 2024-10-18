package com.doceazedo.catraca.events

import com.doceazedo.catraca.Catraca.Companion.AUTH_FLOW_DURATION
import com.doceazedo.catraca.Catraca.Companion.instance
import com.doceazedo.catraca.Catraca.Companion.waitingRoom
import com.doceazedo.catraca.helpers.Messages
import com.doceazedo.catraca.managers.BossBarManager
import com.doceazedo.catraca.managers.CaptchaManager
import com.doceazedo.catraca.managers.GatekeeperManager
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

        instance.launch {
            val identity = IdentityManager.getIdentityFromUsername(event.player.name)

            // handle existing grant
            if (identity != null) {
                val existingGrant = GatekeeperManager.isUserGranted(identity.uuid, event.player)
                if (existingGrant == true) {
                    IdentityManager.loggedInUsers.add(event.player.uniqueId)
                    waitingRoom.enqueuePlayer(event.player)
                    return@launch
                } else if (existingGrant == false) {
                    event.player.kickPlayer("§cNão autorizado!")
                    return@launch
                }
            }

            // new auth flow
            val authFlowCode = GatekeeperManager.createAuthFlow(event.player)
            instance.launch { showTimer(event.player, authFlowCode) }
            instance.launch { sendMessages(event.player, identity != null, authFlowCode) }

            val authFlow = GatekeeperManager.awaitFlowChange(authFlowCode, event.player)
                ?: return@launch event.player.kickPlayer("§cVocê demorou mais de §4${AUTH_FLOW_DURATION}s §cpara fazer login.\nPor favor, tente novamente.")

            if (authFlow.grantKey == null) return@launch event.player.kickPlayer("§cNão autorizado!")

            val isGranted = GatekeeperManager.getGrantByKey(authFlow.grantKey)
            if (isGranted != true) return@launch

            IdentityManager.loggedInUsers.add(event.player.uniqueId)
            waitingRoom.enqueuePlayer(event.player)
        }
    }

    private suspend fun sendMessages(player: Player, isRegistered: Boolean, code: String) {
        repeat((AUTH_FLOW_DURATION / MESSAGES_COOLDOWN).toInt()) {
            if (!player.isOnline) return

            if (CaptchaManager.captchas.contains(player.uniqueId)) {
                CaptchaManager.sendCaptcha(player)
                return@repeat
            }

            if (IdentityManager.loggedInUsers.contains(player.uniqueId)) return
            repeat(30) { player.sendMessage("") }
            if (isRegistered) {
                Messages.sendLoginMessages(player, code)
            } else {
                Messages.sendRegisterMessages(player, code)
            }
            delay(MESSAGES_COOLDOWN * 1000L)
        }
    }

    private suspend fun showTimer(player: Player, code: String) {
        val bar = BossBarManager.create("§e§lFAÇA LOGIN EM: §b§lssn.gg/login/$code", BarColor.GREEN, player)
        val times = ((AUTH_FLOW_DURATION - 2) * 2).toInt()
        repeat(times) {
            if (!player.isOnline || IdentityManager.loggedInUsers.contains(player.uniqueId)) {
                bar.removeAll()
                return
            }

            bar.progress -= 1.0 / times
            delay(500)
        }
    }
}