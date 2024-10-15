package com.doceazedo.catraca.utils

import com.doceazedo.catraca.enums.Env
import com.doceazedo.catraca.enums.Reason
import org.bukkit.entity.Player

val notRegisteredMessage = arrayOf(
    "§aÉ sua §nprimeira vez§a jogando no SSN?",
    "§aSolicite acesso em: §e§l§n${Env.BASE_DOMAIN.value}/registrar",
    "",
    "§6§nJá possui§6 login e quer jogar com esse nome de usuário?",
    "§6Acesse: §c§l§n${Env.BASE_DOMAIN.value}/alt"
).joinToString("\n§r")

fun wrongNicknameCaseMessage (correctName: String, badName: String): String {
    return arrayOf(
        "§4§lSeu nome de usuário está errado!",
        "",
        "§fVocê se cadastrou como: §a$correctName",
        "§fMas está tentando jogar como: §c$badName",
        "",
        "§eEntre com seu nome de usuário correto,",
        "§eou confira seus usuários em §a§l§n${Env.BASE_DOMAIN.value}/alt"
    ).joinToString("\n§r")
}

fun kickPlayer (player: Player, reason: Reason) {
    kickPlayer(player, reason.message)
}

fun kickPlayer (player: Player, reason: String) {
    player.kickPlayer(reason)
}
