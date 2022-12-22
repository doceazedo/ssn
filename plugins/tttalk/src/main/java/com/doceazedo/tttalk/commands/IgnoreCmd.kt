package com.doceazedo.tttalk.commands

import com.doceazedo.tttalk.Tttalk
import com.doceazedo.tttalk.utils.IgnoredManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object IgnoreCmd : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) return false

        if (args == null || args.isEmpty()) {
            val ignoredPlayers = IgnoredManager.getIgnoredPlayers(sender.uniqueId)
            if (ignoredPlayers.size > 0) {
                val ignoredPlayersList = ignoredPlayers.joinToString("§e, §c") { Bukkit.getOfflinePlayer(it).name }
                sender.sendMessage("§eVocê está ignorando §c${ignoredPlayers.size} §ejogador(es): §c${ignoredPlayersList}")
            } else {
                sender.sendMessage("§cVocê não está ignorando ninguém. Para ignorar alguém, use §4/${label} <player>§c.")
            }
            return true
        }

        val targetName = args[0]
        val targetPlayer = Bukkit.getServer().getOfflinePlayer(targetName)

        if (targetPlayer == null) {
            sender.sendMessage("§4${targetName} §cnão existe.")
            return true
        }

        if (targetPlayer.uniqueId == sender.uniqueId) {
            sender.sendMessage("§c§o~~ Você não consegue calar as vozes da sua cabeça.")
            return true
        }

        if (!IgnoredManager.isIgnoringPlayer(sender.uniqueId, targetPlayer.uniqueId)) {
            IgnoredManager.ignorePlayer(sender.uniqueId, targetPlayer.uniqueId)
            sender.sendMessage("§aVocê não receberá mais mensagens de §e${targetName}§a!")
        } else {
            IgnoredManager.stopIgnoringPlayer(sender.uniqueId, targetPlayer.uniqueId)
            sender.sendMessage("§aVocê parou de ignorar §e${targetName}§a!")
        }

        return true
    }
}