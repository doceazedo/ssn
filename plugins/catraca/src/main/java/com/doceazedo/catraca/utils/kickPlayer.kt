package com.doceazedo.catraca.utils

import org.bukkit.entity.Player

enum class Reason(val message: String) {
    PROHIBITED("§cVocê não tem permissão para jogar com esse usuário!"),
    UNAUTHORIZED("§cNão autorizado."),
    EXPIRED_FLOW("§cSeu tempo acabou."),
}

fun kickPlayer (player: Player, reason: Reason) {
    player.kickPlayer(reason.message)
}
