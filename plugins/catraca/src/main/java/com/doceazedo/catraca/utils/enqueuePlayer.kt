package com.doceazedo.catraca.utils

import com.doceazedo.catraca.Catraca
import org.bukkit.entity.Player

fun enqueuePlayer(player: Player) {
    Catraca.waitingRoomApi.enqueuePlayer(player)
}