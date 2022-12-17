package com.github.maizenalegal.sensor.enums

enum class Env(val value: String) {
    IDENTITY_URL(System.getenv("LOCAL_IDENTITY_URL")),
    IDENTITY_TOKEN(System.getenv("CATRACA_IDENTITY_TOKEN"));
}