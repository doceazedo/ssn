package com.doceazedo.catraca.managers

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.Gson
import org.bukkit.Bukkit
import java.util.UUID

object IdentityManager {
    private val API_BASE_URL: String = System.getenv("LOCAL_IDENTITY_URL")
    private val API_TOKEN: String = System.getenv("CATRACA_IDENTITY_TOKEN")
    val loggedInUsers = mutableSetOf<UUID>()
    val registeringUsers = mutableMapOf<UUID, Pair<String, String>>()

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

    data class RegisterRequest(
        val email: String,
        val password: String,
        val username: String,
        val captchaToken: String,
    )

    data class UsernameResponse(
        val username: String,
    )

    data class ErrorResponse(
        val message: String,
    )

    data class LoginResponse(
        val identity: Identity?,
        val token: String?,
        val error: ErrorResponse?,
    )

    fun getIdentityFromUsername(username: String): Identity? {
        val (_, _, result) = Fuel.get("$API_BASE_URL/api/v1/username/$username/identity")
            .authentication()
            .bearer(API_TOKEN)
            .responseObject<IdentityResponse>()
        val (response, error) = result
        return response?.identity
    }

    fun login(emailOrUsername: String, password: String): LoginResponse {
        val (_, response, result) = Fuel.post("$API_BASE_URL/api/v1/login")
            .jsonBody(LoginRequest(
                emailOrUsername,
                password,
                API_TOKEN,
            ))
            .responseObject<IdentityResponse>()
        val (data, error) = result
        val token = response.headers["token"].firstOrNull()
        val errorMessage = Gson().fromJson(error?.response?.body()?.toByteArray()?.let { String(it, Charsets.UTF_8) }, ErrorResponse::class.java)
        return LoginResponse(
            data?.identity,
            token,
            errorMessage
        )
    }

    fun register(email: String, password: String, username: String): Pair<Identity?, ErrorResponse?> {
        val (_, _, result) = Fuel.post("$API_BASE_URL/api/v1/register")
            .jsonBody(RegisterRequest(
                email,
                password,
                username,
                API_TOKEN,
            ))
            .responseObject<IdentityResponse>()
        val (response, error) = result
        val errorMessage = Gson().fromJson(error?.response?.body()?.toByteArray()?.let { String(it, Charsets.UTF_8) }, ErrorResponse::class.java)
        return Pair(response?.identity, errorMessage)
    }

    fun addUsername(username: String, token: String): Pair<Boolean, ErrorResponse?> {
        val (_, _, result) = Fuel.post("$API_BASE_URL/api/v1/username/$username")
            .header(Headers.COOKIE to "token=$token")
            .jsonBody("{\"captchaToken\": \"$API_TOKEN\"}")
            .responseObject<UsernameResponse>()
        val (response, error) = result
        val errorMessage = Gson().fromJson(error?.response?.body()?.toByteArray()?.let { String(it, Charsets.UTF_8) }, ErrorResponse::class.java)
        return Pair(response?.username != null, errorMessage)
    }
}