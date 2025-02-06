package com.doceazedo.catraca.managers

import java.util.*

object IdentityManager {
    val loggedInUsers = mutableSetOf<UUID>()
    val registeringUsers = mutableMapOf<UUID, Pair<String, String>>()
    val authenticatedUsers = mutableSetOf<UUID>()
}
