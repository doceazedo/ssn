package com.doceazedo.waitingroom.utils

import com.doceazedo.waitingroom.WaitingRoom
import com.google.common.io.ByteStreams
import org.bukkit.entity.Player

fun sendPlayer (player: Player, server: String = "main") {
    try {
        val out = ByteStreams.newDataOutput()
        out.writeUTF("Connect")
        out.writeUTF(server)
        player.sendPluginMessage(WaitingRoom.intance, "BungeeCord", out.toByteArray())
    } catch (_: Throwable) { }
}