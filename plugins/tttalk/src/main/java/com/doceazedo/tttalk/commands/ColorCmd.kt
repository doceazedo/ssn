package com.doceazedo.tttalk.commands

import com.doceazedo.tttalk.utils.ChatColorManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ColorCmd : CommandExecutor {
    private val allowedColorsString = ChatColorManager.allowedColors.joinToString("§f, ") { "§$it$it" }

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) return false

        if (!ChatColorManager.hasPermission(sender)) {
            sender.sendMessage("§cPara usar esse comando, considere §6doar §cpara o servidor! Saiba mais em: §6ssn.gg/doar")
            return true
        }

        val color = if (args != null && args.isNotEmpty()) args[0].toCharArray()[0] else null
        val updatedColor = updateNameColor(sender, color)
        if (updatedColor) {
            sender.sendMessage("§aA §${color}cor §ado seu nome foi atualizada!")
        } else {
            sender.sendMessage("§cUse §4/$label <codigo>§c, sendo esses os códigos disponíveis: $allowedColorsString")
        }

        return true
    }

    private fun updateNameColor(player: Player, color: Char?): Boolean {
        if (color == null) return false
        return ChatColorManager.updateNameColor(player, color)
    }
}