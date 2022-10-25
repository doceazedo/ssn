package com.doceazedo.tttalk.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object MsgCmd : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (args == null || args.size < 2) return false

        val recipientName = args[0]
        val senderName = if (sender is Player) sender.displayName else "[CONSOLE]"
        val message = args.drop(1).joinToString(" ")

        // TODO: if recipientName == senderName, return error

        val recipient = Bukkit.getServer().getPlayer(recipientName);
        if (recipient == null || !recipient.isOnline) {
            sender.sendMessage("§4${recipientName} §cnão está online ou não existe.")
            return true
        }

        recipient.sendMessage("§5$senderName diz: §d$message")
        sender.sendMessage("§5Para ${recipient.displayName}: §d$message")
        return true
    }
}