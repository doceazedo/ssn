package com.doceazedo.catraca.commands

import com.doceazedo.catraca.Catraca.Companion.instance
import com.doceazedo.catraca.Catraca.Companion.waitingRoom
import com.doceazedo.catraca.managers.IdentityManager
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object LoginCmd : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) return false
        if (args.isEmpty()) return false

        val email = if (args.size > 1) args[0] else null
        val password = if (args.size == 1) args[0] else args[2]

        instance.launch {
            val identity = IdentityManager.login(
                email ?: sender.name,
                password
            )
            if (identity == null) {
                sender.sendMessage("§cLogin ou senha inválidos!")
                return@launch
            }

            IdentityManager.loggedInUsers.add(sender.uniqueId)

            if (!identity.usernames.contains(sender.name)) {
                val usernameOwner = IdentityManager.getIdentityFromUsername(sender.name)
                if (usernameOwner != null) {
                    sender.sendMessage("§cO usuário §4${sender.displayName} §cnão está vinculado a essa conta!")
                }
                sender.sendMessage("TODO: register username to this identity")
                return@launch
            }

            waitingRoom.enqueuePlayer(sender)
        }

        return true
    }
}