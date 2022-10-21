package com.doceazedo.catraca.gatekeeper

import com.doceazedo.catraca.Catraca
import com.doceazedo.catraca.utils.generateCode
import com.google.gson.Gson
import kotlinx.coroutines.delay
import org.bukkit.entity.Player

data class FlowData(
    val username: String,
    val ip: String,
    val grantKey: String?,
)

data class Flow(
    val code: String,
    val username: String,
    val ip: String,
    val grantKey: String?,
)

object Flows {
    private const val flowDurationSec: Long = 45
    private const val hr = "§2======================================="
    private val loginURL: String = Catraca.instance.config.getString("gatekeeper.loginURL")

    private fun getFlow(code: String): Flow? {
        val value = Catraca.jedis["flows:$code"] ?: return null
        val flow = Gson().fromJson(value, FlowData::class.java)
        return Flow(
                code,
                flow.username,
                flow.ip,
                flow.grantKey
        )
    }

    fun createFlow(player: Player): Flow {
        val code = generateCode()
        val key = "flows:$code"
        val username = player.displayName
        val ip = player.address.address.hostAddress
        val grantKey = null

        val data = FlowData(
            username,
            ip,
            grantKey
        )
        val json = Gson().toJson(data)

        Catraca.jedis[key] = json
        Catraca.jedis.expire(key, flowDurationSec)

        player.sendMessage(hr)
        player.sendMessage(" ")
        player.sendMessage("§aOi, §e${player.displayName}§a!")
        player.sendMessage("§aClique no link abaixo para fazer login:")
        player.sendMessage("§e§l§n${loginURL}/${code}")
        player.sendMessage(" ")
        player.sendMessage(hr)

        return Flow(
            code,
            username,
            ip,
            grantKey
        )
    }

    suspend fun checkFlow(code: String, player: Player): Flow? {
        if (!player.isOnline) return null
        val flow = getFlow(code) ?: return null
        if (flow.grantKey != null) return Flow(code, flow.username, flow.ip, flow.grantKey)
        delay(500)
        return checkFlow(code, player)
    }
}