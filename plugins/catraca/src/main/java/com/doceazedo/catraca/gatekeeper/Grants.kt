package com.doceazedo.catraca.gatekeeper

import com.doceazedo.catraca.Catraca
import org.bukkit.entity.Player

object Grants {
    private fun getGrant(ownerUUID: String, ip: String, username: String): Boolean? {
        return getGrantByKey("grants:$ownerUUID:$ip:$username")
    }

    fun getGrantByKey(key: String): Boolean? {
        val value = Catraca.jedis[key] ?: return null
        return value == "true"
    }

    fun isUserGranted(ownerUUID: String, player: Player): Boolean? {
        val ip = player.address.address.hostAddress
        val username = player.displayName
        val isUsernameGranted = getGrant(ownerUUID, ip, username)
        if (isUsernameGranted != null) return isUsernameGranted
        return getGrant(ownerUUID, ip, "*")
    }
}