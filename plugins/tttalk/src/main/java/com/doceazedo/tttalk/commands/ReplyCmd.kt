package com.doceazedo.tttalk.commands

import com.doceazedo.tttalk.Tttalk
import com.doceazedo.tttalk.utils.sendPM
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ReplyCmd : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (args == null || args.isEmpty()) return false
        if (sender !is Player) return false

        val lastChatterUUID = Tttalk.lastChatters[sender.uniqueId]
        if (lastChatterUUID == null) {
            sender.sendMessage("§cVocê não está conversando com ninguém.")
            return true
        }

        val recipient = Bukkit.getServer().getPlayer(lastChatterUUID);
        if (recipient == null) {
            sender.sendMessage("§cEsse usuário não está online ou não existe.")
            return true
        }

        if (!recipient.isOnline) {
            sender.sendMessage("§4${recipient.displayName} §cnão está mais online.")
            return true
        }

        val message = args.joinToString(" ")
        sendPM(sender, recipient, message)

        return true
    }
}