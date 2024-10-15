package com.doceazedo.catraca.managers

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.Gson
import java.util.UUID

object IdentityManager {
    private val API_BASE_URL: String = System.getenv("LOCAL_IDENTITY_URL")
    private val API_TOKEN: String = System.getenv("CATRACA_IDENTITY_TOKEN")
    val loggedInUsers = mutableSetOf<UUID>()

    data class Identity(
        val uuid: String,
        val email: String,
        val verified: Boolean,
        val primaryUsername: String,
        val role: String,
        val isBanned: Boolean,
        val createdAt: String,
        val usernames: Array<String>,
    )

    data class IdentityResponse(
        val identity: Identity
    )

    data class LoginRequest(
        val login: String,
        val password: String,
        val captchaToken: String,
    )

    fun getIdentityFromUsername(username: String): Identity? {
        val (_, _, result) = Fuel.get("$API_BASE_URL/api/v1/username/$username/identity")
            .authentication()
            .bearer(API_TOKEN)
            .responseObject<IdentityResponse>()
        val (response, error) = result
        return response?.identity
    }

    fun login(emailOrUsername: String, password: String): Identity? {
        val (_, _, result) = Fuel.post("$API_BASE_URL/api/v1/login")
            .jsonBody(Gson().toJson(LoginRequest(
                emailOrUsername,
                password,
                API_TOKEN,
            )))
            .responseObject<IdentityResponse>()
        val (response, error) = result
        return response?.identity
    }
}