package com.doceazedo.catraca.utils

import com.doceazedo.catraca.Catraca
import org.bukkit.entity.Player

val websiteURL: String = Catraca.instance.config.getString("websiteURL")

val notRegisteredMessage = arrayOf(
    "§aÉ sua §nprimeira vez§a jogando no SSN?",
    "§aSolicite acesso em: §e§l§n$websiteURL/registrar",
    "",
    "§6§nJá possui§6 login e quer jogar com esse nome de usuário?",
    "§6Acesse: §c§l§n$websiteURL/alt"
).joinToString("\n§r")

enum class Reason(val message: String) {
    PROHIBITED("§cVocê não tem permissão para jogar com esse usuário!"),
    UNAUTHORIZED("§cNão autorizado."),
    EXPIRED_FLOW("§cSeu tempo acabou."),
    NOT_REGISTERED(notRegisteredMessage),
}

fun kickPlayer (player: Player, reason: Reason) {
    player.kickPlayer(reason.message)
}
