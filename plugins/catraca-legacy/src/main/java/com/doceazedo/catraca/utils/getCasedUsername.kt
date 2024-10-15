package com.doceazedo.catraca.utils

import com.doceazedo.catraca.identity.Username.Identity

data class CasedUsername(
    val username: String,
    val casedUsername: String,
    val isCorrectlyCased: Boolean,
)

fun getCasedUsername(identity: Identity, username: String): CasedUsername {
    val casedUsername = identity.usernames.find { it.lowercase() == username.lowercase() } ?: ""
    val isCorrectlyCased = username == casedUsername
    return CasedUsername(
        username,
        casedUsername,
        isCorrectlyCased
    )
}