package com.doceazedo.catraca.commands

import com.doceazedo.catraca.Catraca.Companion.instance
import com.doceazedo.catraca.managers.CaptchaManager
import com.doceazedo.catraca.managers.IdentityManager
import com.doceazedo.catraca.managers.PocketbaseManager
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object RegisterCmd : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) return false
        if (args.size != 3) return false

        val (email, password, confirmPassword) = args

        if (password != confirmPassword) {
            sender.sendMessage("§cAs senhas não conferem. Por favor, tente novamente.")
            return false
        }

        if (CaptchaManager.captchas.contains(sender.uniqueId)) return true

        instance.launch {
            val username = PocketbaseManager.findUsername(sender.name)
            if (username != null) {
                sender.sendMessage("§cEsse nome de usuário já está em uso!")
                return@launch
            }
            IdentityManager.registeringUsers[sender.uniqueId] = Pair(email, password)
            CaptchaManager.sendCaptcha(sender)
        }

        return true
    }
}