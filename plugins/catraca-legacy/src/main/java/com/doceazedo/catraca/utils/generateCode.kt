package com.doceazedo.catraca.utils

import java.util.*

fun generateCode(): String {
    return UUID.randomUUID().toString().slice(0..4)
}