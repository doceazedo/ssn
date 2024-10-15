package com.doceazedo.catraca.enums

import com.doceazedo.catraca.utils.notRegisteredMessage

enum class Reason(val message: String) {
    PROHIBITED("§cVocê não tem permissão para jogar com esse usuário!"),
    UNAUTHORIZED("§cNão autorizado."),
    EXPIRED_FLOW("§cSeu tempo acabou."),
    NOT_REGISTERED(notRegisteredMessage),
}