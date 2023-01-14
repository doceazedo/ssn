package com.doceazedo.tttalk.commands

import com.doceazedo.tttalk.Tttalk
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object RulesCmd : CommandExecutor {
    private val rules = Tttalk.instance.config.getStringList("rules")

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) return false

        sender.sendMessage(" ")
        sender.sendMessage("§4§lREGRAS DO SERVIDOR")
        sender.sendMessage(" ")
        rules.forEachIndexed { i, rule ->
            sender.sendMessage("§4${i + 1}. §c$rule")
        }
        sender.sendMessage(" ")

        return true
    }
}