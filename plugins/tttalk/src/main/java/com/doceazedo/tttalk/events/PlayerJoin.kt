package com.doceazedo.tttalk.events

import com.doceazedo.tttalk.Tttalk
import com.doceazedo.tttalk.utils.ChatColorManager
import com.doceazedo.tttalk.utils.WorldManager
import com.doceazedo.tttalk.utils.IgnoredManager
import com.viaversion.viaversion.api.Via
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File


object PlayerJoin : Listener {
    private val serverCreatedAt = Tttalk.instance.config.getString("server.created")
    private val serverVersion = Tttalk.instance.config.getInt("server.version")

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        // Load player chat data into memory
        IgnoredManager.loadIgnoredPlayers(e.player.uniqueId)
        ChatColorManager.loadNameColor(e.player)

        // Show custom join message
        e.joinMessage = "§8[§a+§8] ${e.player.displayName} entrou"

        // Show MOTD
        val playerCount = File(WorldManager.worldDir, "playerdata").listFiles()?.size
        e.player.sendMessage(" ")
        e.player.sendMessage("§aOlá, §e${e.player.displayName}§a!")
        e.player.sendMessage("§e$playerCount §ajogadores já entraram nesse servidor")
        e.player.sendMessage("§aO mundo tem §e${WorldManager.age} dias §ade vida e pesa §e${WorldManager.size}§a")
        e.player.sendMessage("§aO servidor foi criado em §e$serverCreatedAt §ae está em sua §e${serverVersion}ª §aversão")
        e.player.sendMessage(" ")


        // https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol_version_numbers
        val api = Via.getAPI()
        val version = api.getPlayerVersion(e.player)
        if (version > 772) { // > 1.21.8
            e.player.sendMessage("§6§l[§e§l!§6§l] §e§lVocê está usando uma versão acima da §6§l1.21.8§e§l. Em caso de problemas, recomendamos usar a §6§l1.21.8§e§l.")
            e.player.sendMessage(" ")
        } else if (version < 755) { // < 1.17
            e.player.sendMessage("§4§l[§c§l!§4§l] §c§lVocê está usando uma versão abaixo da §4§l1.17§c§l. Você não poderá ver abaixo do nível 0. Se possível, use a versão §4§l1.21.8§c§l.")
            e.player.sendMessage(" ")
        } else if (version < 767) { // < 1.21
            e.player.sendMessage("§6§l[§e§l!§6§l] §e§lVocê está usando uma versão abaixo da §6§l1.21§e§l. Em caso de problemas, recomendamos usar a §6§l1.21.8§e§l.")
            e.player.sendMessage(" ")
        }

        e.player.playSound(e.player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
    }
}