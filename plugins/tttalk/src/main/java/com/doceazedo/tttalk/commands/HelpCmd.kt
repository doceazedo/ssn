package com.doceazedo.tttalk.commands

import com.doceazedo.tttalk.Tttalk
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


object HelpCmd : CommandExecutor {
    private val commands = Tttalk.instance.config.getMapList("commands")

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) return false

        sender.sendMessage(" ")
        sender.sendMessage("§6§lCOMANDOS DISPONÍVEIS")
        sender.sendMessage(" ")
        for (command in commands) {
            val label = command["label"]
            val help = command["help"]

            sender.sendMessage("§c/$label §e- $help")
        }
        sender.sendMessage(" ")

        return true
    }
}