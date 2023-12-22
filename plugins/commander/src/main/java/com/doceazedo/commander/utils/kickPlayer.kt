package com.doceazedo.commander.utils

import com.doceazedo.commander.Commander
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.Bukkit

fun kickPlayer(playerName: String, reason: String) {
    Commander.instance.launch {
        val player = Bukkit.getPlayer(playerName)
        player?.kickPlayer(reason)
    }
}
