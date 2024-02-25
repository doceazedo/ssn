package com.doceazedo.waitingroom.utils

import com.doceazedo.waitingroom.WaitingRoom

object PlayerCount {
    private fun getOnlinePlayers(): Int {
        val playerCount = WaitingRoom.jedis["player-count"] ?: return 0
        return playerCount.toInt()
    }

    private fun getMaxPlayers(): Int {
        val maxPlayers = WaitingRoom.jedis["max-players"] ?: return 0
        return maxPlayers.toInt()
    }

    fun getAvailableSlots(): Int {
        return getMaxPlayers() - getOnlinePlayers()
    }

    fun isFull(): Boolean {
        return getOnlinePlayers() >= getMaxPlayers()
    }
}