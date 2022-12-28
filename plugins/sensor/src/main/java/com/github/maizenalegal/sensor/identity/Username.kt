package com.github.maizenalegal.sensor.identity

import com.github.maizenalegal.sensor.enums.Env
import com.google.gson.Gson
import khttp.responses.Response
import org.bukkit.Bukkit
import org.json.JSONObject

object Username {
    data class Username(
        val name: String,
        val ownerId: String,
        val createdAt: String,
        val firstJoin: String?,
        val lastSeen: String?,
        val playedSeconds: Int,
        val joinCount: Int,
        val isOnline: Boolean
    )

    fun getUsername(username: String): Username? {
        return try {
            val resp: Response = khttp.get(
                "${Env.IDENTITY_URL.value}/api/v1/username/$username",
                mapOf("Authorization" to "Bearer ${Env.IDENTITY_TOKEN.value}")
            )
            val data: JSONObject = resp.jsonObject
            Gson().fromJson(data["username"].toString(), Username::class.java)
        } catch (cause: Throwable) {
            Bukkit.getLogger().warning("An error ocurred when trying to fetch /api/v1/username/$username")
            cause.printStackTrace()
            null
        }
    }

    fun logUsernameQuit(username: String) {
        try {
            khttp.post(
                "${Env.IDENTITY_URL.value}/api/v1/username/$username/quit",
                mapOf("Authorization" to "Bearer ${Env.IDENTITY_TOKEN.value}")
            )
        } catch (cause: Throwable) {
            Bukkit.getLogger().warning("An error ocurred when trying to fetch /api/v1/username/$username/quit")
            cause.printStackTrace()
        }
    }
}