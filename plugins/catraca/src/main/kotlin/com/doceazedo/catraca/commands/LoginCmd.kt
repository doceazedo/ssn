package com.doceazedo.catraca.commands

import com.doceazedo.catraca.Catraca.Companion.instance
import com.doceazedo.catraca.Catraca.Companion.waitingRoom
import com.doceazedo.catraca.managers.CaptchaManager
import com.doceazedo.catraca.managers.GatekeeperManager
import com.doceazedo.catraca.managers.IdentityManager
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.Bukkit
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

        val emailOrUsername = if (args.size == 1) sender.name else args[0]
        val password = if (args.size == 1) args[0] else args[1]

        instance.launch {
            val (identity, token, loginError) = IdentityManager.login(
                emailOrUsername,
                password
            )
            if (loginError != null || identity == null || token == null) {
                sender.kickPlayer("§c${loginError?.message ?: "Login ou senha inválidos!"}")
                return@launch
            }

            if (!identity.usernames.contains(sender.name)) {
                val usernameOwner = IdentityManager.getIdentityFromUsername(sender.name)
                if (usernameOwner != null) {
                    sender.kickPlayer("§cO nome de usuário §4${sender.displayName} §cnão está vinculado a essa conta!")
                    return@launch
                }

                IdentityManager.authenticatedUsers[sender.uniqueId] = Pair(identity, token)
                CaptchaManager.sendCaptcha(sender)

                return@launch
            }

            IdentityManager.loggedInUsers.add(sender.uniqueId)
            GatekeeperManager.grantUser(identity.uuid, sender, true)
            waitingRoom.enqueuePlayer(sender)
        }

        return true
    }
}