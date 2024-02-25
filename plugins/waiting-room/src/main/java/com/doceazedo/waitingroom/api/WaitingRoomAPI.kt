package com.doceazedo.waitingroom.api

import com.doceazedo.waitingroom.managers.QueueManager
import org.bukkit.entity.Player

object WaitingRoomAPI {
    fun enqueuePlayer(player: Player) {
        QueueManager.enqueue(player)
    }
}