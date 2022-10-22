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

fun kickPlayer (player: Player, reason: Reason) {
    player.kickPlayer(reason.message)
}
