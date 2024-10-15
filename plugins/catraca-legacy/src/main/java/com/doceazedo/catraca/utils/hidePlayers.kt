package com.doceazedo.catraca.utils

import com.doceazedo.catraca.Catraca
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun hidePlayers(me: Player) {
    Bukkit.getServer().onlinePlayers.forEach {
        if (it != me) {
            me.hidePlayer(Catraca.instance, it)
            it.hidePlayer(Catraca.instance, me)
        }
    }
}