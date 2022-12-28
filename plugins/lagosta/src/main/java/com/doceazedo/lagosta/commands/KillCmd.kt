package com.doceazedo.lagosta.commands

import com.doceazedo.lagosta.modules.EndCrystalManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object KillCmd: CommandExecutor {
    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) return false
        EndCrystalManager.crystalEntityDamager.remove(sender.uniqueId)
        sender.health = 0.0
        return true
    }
}