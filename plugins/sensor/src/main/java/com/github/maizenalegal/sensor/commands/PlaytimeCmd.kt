package com.github.maizenalegal.sensor.commands

import com.github.maizenalegal.sensor.identity.Username.getUsername
import com.github.maizenalegal.sensor.utils.TimeFormatter
import com.github.shynixn.mccoroutine.bukkit.SuspendingCommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object PlaytimeCmd : SuspendingCommandExecutor {
    override suspend fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) return false
        val target = if (args.isEmpty()) sender.displayName else args[0]
        val user = getUsername(target)

        if (user?.firstJoin == null) {
            sender.sendMessage("§cEsse jogador nunca entrou nesse servidor ou não existe.")
            return true
        }

        val joinDate = TimeFormatter.dateFormatter.parse(user.firstJoin)
        val formattedJoinDate = TimeFormatter.formatDate(joinDate)
        val playTime = TimeFormatter.formatDuration(user.playedSeconds)

        sender.sendMessage("§e${user.name} §aentrou no servidor em §e${formattedJoinDate} §ae jogou por §e${playTime}§a.")
        return true
    }
}