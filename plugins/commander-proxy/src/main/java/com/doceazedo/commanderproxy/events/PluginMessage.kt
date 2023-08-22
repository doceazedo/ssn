package com.doceazedo.commanderproxy.events

import com.doceazedo.commanderproxy.CommanderProxy
import com.doceazedo.commanderproxy.utils.Console
import net.md_5.bungee.api.connection.Server
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.io.ByteArrayInputStream
import java.io.DataInputStream

object PluginMessage: Listener {
    @EventHandler
    fun onPluginMessage(e: PluginMessageEvent) {
        if (e.tag != "commander:exec") return;
        if (e.sender !is Server) return;

        val stream = ByteArrayInputStream(e.data);
        val input = DataInputStream(stream);
        val command = input.readUTF();

        CommanderProxy.instance.logger.info("Executing Commander command: $command");
        Console.exec(command);
    }
}