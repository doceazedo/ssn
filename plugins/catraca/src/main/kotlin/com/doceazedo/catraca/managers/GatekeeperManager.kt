package com.doceazedo.catraca.managers

import com.doceazedo.catraca.Catraca.Companion.AUTH_FLOW_DURATION
import com.doceazedo.catraca.Catraca.Companion.jedis
import com.google.gson.Gson
import kotlinx.coroutines.delay
import org.bukkit.entity.Player
import java.util.*

object GatekeeperManager {
    private var flows = mutableMapOf<UUID, String>()

    data class AuthFlow(
        val username: String,
        val ip: String,
        val grantKey: String?, // grantId
    )

    private fun getGrant(ownerUuid: String, ip: String, username: String): Boolean? {
        return getGrantByKey("grants:$ownerUuid:$ip:${username.lowercase()}")
    }

    fun getGrantByKey(key: String): Boolean? {
        val value = jedis[key] ?: return null
        return value == "true"
    }

    fun isUserGranted(ownerUUID: String, player: Player): Boolean? {
        val ip = player.address?.address?.hostAddress ?: "0.0.0.0"
        val username = player.displayName
        val isUsernameGranted = getGrant(ownerUUID, ip, username)
        if (isUsernameGranted != null) return isUsernameGranted
        return getGrant(ownerUUID, ip, "*")
    }

    fun createAuthFlow(player: Player): String {
        val code = UUID.randomUUID().toString().slice(0..4).uppercase()
        val key = "flows:$code"
        val username = player.displayName
        val ip = player.address?.address?.hostAddress ?: "0.0.0.0"
        val grantKey = null
        val flow = AuthFlow(
            username,
            ip,
            grantKey
        )

        jedis[key] = Gson().toJson(flow)
        jedis.expire(key, AUTH_FLOW_DURATION)

        flows[player.uniqueId] = code

        return code
    }

    private fun getAuthFlow(code: String): AuthFlow? {
        return Gson().fromJson(
            jedis["flows:$code"] ?: return null,
            AuthFlow::class.java
        )
    }

    suspend fun awaitFlowChange(code: String, player: Player): AuthFlow? {
        if (!player.isOnline) return null // TODO: delete flow
        val flow = getAuthFlow(code) ?: return null // TODO: delete flow
        if (flow.grantKey != null) {
            // TODO: delete flow
            return flow
        }
        delay(500)
        return awaitFlowChange(code, player)
    }
}