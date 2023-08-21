package com.doceazedo.commander.utils

import com.doceazedo.commander.Commander
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.Bukkit
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream


object Console {
    private val console = Bukkit.getServer().consoleSender;

    fun run (command: String, proxy: Boolean = false) {
        if (proxy) return runProxy(command);

        // Minecraft main thread
        Commander.instance.launch {
            Bukkit.dispatchCommand(console, command)
        }
    }

    private fun runProxy(command: String) {
        // Minecraft main thread
        val b = ByteArrayOutputStream()
        val out = DataOutputStream(b)
        try {
            out.writeUTF("Message")
            out.writeUTF("ALL")
            out.writeUTF(command)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Bukkit.getServer().sendPluginMessage(Commander.instance, "BungeeCord", b.toByteArray())
    }
}