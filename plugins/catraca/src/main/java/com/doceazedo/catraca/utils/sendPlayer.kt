package com.doceazedo.catraca.utils

import com.doceazedo.catraca.Catraca
import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import org.bukkit.entity.Player

val targetServer: String = Catraca.instance.config.getString("targetServer")

fun sendPlayer (player: Player, server: String = targetServer) {
    try {
        val out = ByteStreams.newDataOutput()
        out.writeUTF("Connect")
        out.writeUTF(server)
        player.sendPluginMessage(Catraca.instance, "BungeeCord", out.toByteArray())
    } catch (cause: Throwable) {
        Bukkit.getLogger().warning("Não foi possível redirecionar ${player.displayName}")
        cause.printStackTrace()
    }
}
