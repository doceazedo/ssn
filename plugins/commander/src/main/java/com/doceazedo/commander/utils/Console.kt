package com.doceazedo.commander.utils

import com.doceazedo.commander.Commander
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.Bukkit
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream


object Console {
    private val console = Bukkit.getServer().consoleSender;

    fun exec (command: String, proxy: Boolean = false) {
        if (proxy) return execProxy(command);

        // Minecraft main thread
        Commander.instance.launch {
            Bukkit.dispatchCommand(console, command)
        }
    }

    private fun execProxy(command: String) {
        val stream = ByteArrayOutputStream()
        val out = DataOutputStream(stream)
        out.writeUTF(command)
        Commander.instance.server.sendPluginMessage(Commander.instance, "commander:exec", stream.toByteArray())
    }
}