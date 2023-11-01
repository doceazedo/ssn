package com.doceazedo.commander.enums

enum class Env(val value: String) {
    COMMANDER_TOKEN(System.getenv("COMMANDER_TOKEN"))
}