package com.github.maizenalegal.sensor.utils

import com.github.maizenalegal.sensor.Sensor

object PlayerCount {
    fun update() {
        val count = Sensor.instance.server.onlinePlayers.size.toString()
        Sensor.jedis["player-count"] = count
    }

    fun updateMax() {
        val max = Sensor.instance.server.maxPlayers.toString()
        Sensor.jedis["max-players"] = max
    }
}