package com.doceazedo.catraca.identity

import com.doceazedo.catraca.Catraca
import com.google.gson.Gson
import khttp.responses.Response
import org.bukkit.Bukkit
import org.json.JSONObject

data class Identity(
    val uuid: String,
    val email: String,
    val verified: Boolean,
    val primaryUsername: String,
    val usernames: Array<String>
)

object Whois {
    private val identityURL: String = Catraca.instance.config.getString("identityURL")

    fun whois (username: String): Identity? {
        return try {
            val resp: Response = khttp.get("$identityURL/api/v1/whois/$username")
            val data: JSONObject = resp.jsonObject
            Gson().fromJson(data["identity"].toString(), Identity::class.java)
        } catch (cause: Throwable) {
            Bukkit.getLogger().warning("An error ocurred when trying to fetch $identityURL/api/v1/whois/$username")
            cause.printStackTrace()
            null
        }
    }
}