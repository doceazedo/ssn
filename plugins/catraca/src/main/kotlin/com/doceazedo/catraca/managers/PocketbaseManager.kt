package com.doceazedo.catraca.managers

import com.doceazedo.catraca.helpers.Messages
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import org.bukkit.Bukkit
import java.net.URLEncoder
import java.util.regex.Pattern

object PocketbaseManager {
    private val BASE_URL = System.getenv("LOCAL_POCKETBASE_URL")
    private val SUPER_USER_IDENTITY = System.getenv("PB_SUPERUSER_IDENTITY")
    private val SUPER_USER_PASSWORD = System.getenv("PB_SUPERUSER_PASSWORD")

    private lateinit var token: String

    private var emailValidator = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

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
    )

    data class UsernamesResponse(
        val items: List<UsernameRecord>
    )

    data class ListResponse(
        val page: Int,
        val perPage: Int,
        val totalItems: Int,
        val totalPages: Int,
    )

    data class RegisterRequest(
        val email: String,
        val password: String,
        val passwordConfirm: String,
        val emailVisibility: Boolean,
    )

    data class CreateUsernameRequest(
        val name: String,
        val owner: String,
    )

    data class UpdatePrimaryUsernameRequest(
        val primaryUsername: String,
    )

    data class EmailVerificationRequest(
        val email: String,
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

    private fun isEmailAvailable(email: String): Boolean {
        val (_, _, result) = Fuel
            .get("$BASE_URL/api/collections/users/records?page=1&perPage=1&filter=${URLEncoder.encode("email=\"$email\"", "utf-8")}")
            .header(Headers.AUTHORIZATION, token)
            .responseObject<ListResponse>()
        val (data) = result
        if (data == null) {
            return false
        }
        return data.totalItems == 0
    }

    private fun createUser(email: String, password: String): AuthRecord? {
        val (_, _, result) = Fuel
            .post("$BASE_URL/api/collections/users/records")
            .header(Headers.AUTHORIZATION, token)
            .jsonBody(RegisterRequest(
                email,
                password,
                password,
                false,
            ))
            .responseObject<AuthRecord>()
        val (data) = result
        return data
    }

    fun createUsername(name: String, owner: String): UsernameRecord? {
        val (_, _, result) = Fuel
            .post("$BASE_URL/api/collections/usernames/records")
            .header(Headers.AUTHORIZATION, token)
            .jsonBody(CreateUsernameRequest(
                name,
                owner,
            ))
            .responseObject<UsernameRecord>()
        val (data) = result
        return data
    }

    private fun setPrimaryUsername(ownerId: String, nameId: String): Boolean {
        val (_, _, result) = Fuel
            .patch("$BASE_URL/api/collections/users/records/$ownerId")
            .header(Headers.AUTHORIZATION, token)
            .jsonBody(UpdatePrimaryUsernameRequest(nameId))
            .responseObject<UsernameRecord>()
        val (data) = result
        return data != null
    }

    private fun requestVerification(email: String) {
        Fuel
            .post("$BASE_URL/api/collections/users/request-verification")
            .header(Headers.AUTHORIZATION, token)
            .jsonBody(EmailVerificationRequest(email))
            .response()
    }

    fun register(email: String, password: String, name: String): Pair<AuthRecord?, String> {
        val isValidEmail = emailValidator
            .matcher(email)
            .matches()
        if (!isValidEmail) {
            return Pair(null, "§cO e-mail informado é inválido.")
        }

        if (!isEmailAvailable(email)) {
            return Pair(null, Messages.takenEmailMessage(email, name))
        }

        val user = createUser(email, password) ?: return Pair(null, "§cNão foi possível registrar sua conta, por favor, tente novamente.")
        val username = createUsername(name, user.id) ?: return Pair(null, "§cNão foi possível registrar esse nome de usuário, por favor, tente novamente.")
        setPrimaryUsername(user.id, username.id)
        requestVerification(email)

        return Pair(user, "")
    }

    fun login(identity: String, password: String): AuthRecord? {
        val (_, _, result) = Fuel
            .post("$BASE_URL/api/collections/users/auth-with-password")
            .jsonBody(LoginRequest(
                identity,
                password,
            ))
            .responseObject<LoginResponse>()
        val (data) = result
        return data?.record
    }
}