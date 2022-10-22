package com.doceazedo.catraca.enums

enum class Env(val value: String) {
    BASE_DOMAIN(System.getenv("PUBLIC_BASE_DOMAIN")),
    IDENTITY_URL(System.getenv("LOCAL_IDENTITY_URL")),
    GATEKEEPER_URL(System.getenv("PUBLIC_GATEKEEPER_URL")),
    REDIS_HOST(System.getenv("GK_REDIS_HOST")),
    REDIS_PORT(System.getenv("GK_REDIS_PORT")),
    REDIS_PASSWORD(System.getenv("GK_REDIS_PASSWORD")),
}