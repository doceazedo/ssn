package com.doceazedo.waitingroom.enums

enum class Env(val value: String) {
    REDIS_HOST(System.getenv("GK_REDIS_HOST")),
    REDIS_PORT(System.getenv("GK_REDIS_PORT")),
    REDIS_PASSWORD(System.getenv("GK_REDIS_PASSWORD"))
}