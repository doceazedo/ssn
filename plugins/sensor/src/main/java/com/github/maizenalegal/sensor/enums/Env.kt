package com.github.maizenalegal.sensor.enums

enum class Env(val value: String) {
    IDENTITY_URL(System.getenv("LOCAL_IDENTITY_URL")),
    IDENTITY_TOKEN(System.getenv("CATRACA_IDENTITY_TOKEN")),
    REDIS_HOST(System.getenv("GK_REDIS_HOST")),
    REDIS_PORT(System.getenv("GK_REDIS_PORT")),
    REDIS_PASSWORD(System.getenv("GK_REDIS_PASSWORD"))
}