package com.doceazedo.catraca.commands

import com.doceazedo.catraca.Catraca.Companion.instance
import com.doceazedo.catraca.Catraca.Companion.waitingRoom
import com.doceazedo.catraca.managers.CaptchaManager
import com.doceazedo.catraca.managers.GatekeeperManager
import com.doceazedo.catraca.managers.IdentityManager
import com.doceazedo.catraca.managers.PocketbaseManager
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
            val user = PocketbaseManager.login(emailOrUsername, password)
            if (user == null) {
                sender.kickPlayer("§cLogin ou senha inválidos!")
                return@launch
            }

            val username = PocketbaseManager.findUsername(sender.name)

            // new alt username if available
            if (username == null) {
                IdentityManager.authenticatedUsers.add(sender.uniqueId)
                CaptchaManager.sendCaptcha(sender)
                return@launch
            }

            if (username.owner != user.id) {
                sender.kickPlayer("§cO nome de usuário §4${sender.displayName} §cnão está vinculado a essa conta!")
                return@launch
            }

            IdentityManager.loggedInUsers.add(sender.uniqueId)
            GatekeeperManager.grantUser(user.id, sender, true)
            waitingRoom.enqueuePlayer(sender)
        }

        return true
    }
}