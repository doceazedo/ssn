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

    fun grantUser(ownerUUID: String, player: Player, allowAlts: Boolean): Pair<String, Boolean> {
        val ip = player.address?.address?.hostAddress ?: "0.0.0.0"
        val usernameOrStar = if (allowAlts) "*" else player.displayName.lowercase()
        val key = "grants:$ownerUUID:$ip:${usernameOrStar}"
        jedis[key] = "true"
        // jedis.expire(key, GRANT_DURATION)
        return Pair(key, true)
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

    fun deleteUserAuthFlow(player: Player): Boolean {
        val code = flows[player.uniqueId] ?: return false
        return deleteAuthFlowByCode(code)
    }

    private fun deleteAuthFlowByCode(code: String): Boolean {
        return jedis.del("flows:$code") == 1L
    }

    suspend fun awaitFlowChange(code: String, player: Player): AuthFlow? {
        if (!player.isOnline) return null
        val flow = getAuthFlow(code) ?: return null
        if (flow.grantKey != null) return flow

        delay(500)
        return awaitFlowChange(code, player)
    }
}