package com.github.maizenalegal.sensor.managers

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import java.net.URLEncoder
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeFormatter

object PocketbaseManager {
    private val BASE_URL = System.getenv("LOCAL_POCKETBASE_URL")
    private val SUPER_USER_IDENTITY = System.getenv("PB_SUPERUSER_IDENTITY")
    private val SUPER_USER_PASSWORD = System.getenv("PB_SUPERUSER_PASSWORD")

    private lateinit var token: String

    data class LoginRequest(
        val identity: String,
        val password: String,
    )

    data class AuthRecord(
        val collectionId: String,
        val collectionName: String,
        val created: String,
        val email: String,
        val emailVisibility: Boolean,
        val id: String,
        val updated: String,
        val verified: Boolean,
    )

    data class LoginResponse(
        val record: AuthRecord,
        val token: String,
    )

    data class UsernameRecord(
        val id: String,
        val name: String,
        val owner: String,
        val firstJoin: String?,
        val lastSeen: String?,
        val playedSeconds: Int,
        val joinCount: Int,
        val online: Boolean,
    )

    data class UsernamesResponse(
        val items: List<UsernameRecord>
    )

    data class UpdateUsernameRequest(
        val firstJoin: String?,
        val lastSeen: String,
        val playedSeconds: Int,
        val joinCount: Int,
        val online: Boolean,
    )

    fun loginAsSuperUser() {
        val (_, _, result) = Fuel
            .post("$BASE_URL/api/collections/_superusers/auth-with-password")
            .jsonBody(LoginRequest(
                SUPER_USER_IDENTITY,
                SUPER_USER_PASSWORD,
            ))
            .responseObject<LoginResponse>()
        val (data) = result
        if (data != null) {
            token = data.token
        }
    }

    fun findUsername(name: String): UsernameRecord? {
        val (_, _, result) = Fuel
            .get("$BASE_URL/api/collections/usernames/records?page=1&perPage=1&filter=${URLEncoder.encode("name:lower=\"${name.lowercase()}\"", "utf-8")}")
            .header(Headers.AUTHORIZATION, token)
            .responseObject<UsernamesResponse>()
        val (data) = result
        if (data == null) {
            return null
        }
        return data.items.firstOrNull()
    }

    fun logUsernameJoin(displayName: String): Boolean {
        val username = findUsername(displayName) ?: return false

        val (_, _, result) = Fuel
            .patch("$BASE_URL/api/collections/usernames/records/${username.id}")
            .header(Headers.AUTHORIZATION, token)
            .jsonBody(UpdateUsernameRequest(
                if (username.lastSeen != null) { Instant.now().toString() } else { username.firstJoin },
                Instant.now().toString(),
                username.playedSeconds,
                username.joinCount + 1,
                true
            ))
            .responseObject<UsernameRecord>()
        val (data) = result
        return data != null
    }

    fun logUsernameQuit(displayName: String): Boolean {
        val username = findUsername(displayName) ?: return false

        val dateFormatter = DateTimeFormatter.ISO_INSTANT
        val lastSeen = if (username.lastSeen != null) { Instant.from(dateFormatter.parse(username.lastSeen.replace(" ", "T"))) } else { Instant.now() }
        val sessionDuration = Duration.between(lastSeen, Instant.now())

        val (_, _, result) = Fuel
            .patch("$BASE_URL/api/collections/usernames/records/${username.id}")
            .header(Headers.AUTHORIZATION, token)
            .jsonBody(UpdateUsernameRequest(
                username.firstJoin,
                Instant.now().toString(),
                username.playedSeconds + sessionDuration.seconds.toInt(),
                username.joinCount,
                false
            ))
            .responseObject<UsernameRecord>()
        val (data) = result
        return data != null
    }
}