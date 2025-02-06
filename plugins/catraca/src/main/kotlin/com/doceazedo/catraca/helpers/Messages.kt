package com.doceazedo.catraca.helpers

import org.bukkit.entity.Player

object Messages {
    fun sendLoginMessages(player: Player, code: String) {
        arrayOf(
            "                  §e§lBoas-vindas ao §b§lSSN§e§l!",
            "",
            "      §5§lLOGIN COM §9§lDISCORD§5§l: §d§nssn.gg/login/$code",
            "",
            "                   §2§lACESSE SUA CONTA:",
            "                        §a/login §2<§asenha§2>",
            "",
            " §7§lESQUECEU SUA SENHA? §f§nssn.gg/redefinir-senha",
            " "
        ).forEach { player.sendMessage(it) }
    }

    fun sendRegisterMessages(player: Player, code: String) {
        arrayOf(
            "               §e§lBoas-vindas ao §b§lSSN§e§l!",
            "",
            "   §5§lLOGIN COM §9§lDISCORD§5§l: §d§nssn.gg/login/$code",
            "",
            "        §2§lÉ SUA PRIMEIRA VEZ JOGANDO?",
            " §a/registrar §2<§aemail§2> <§asenha§2> <§aconfirmar-senha§2>",
            "",
            "               §7§lJÁ TEM UMA CONTA?",
            "               §f/login §7<§femail§7> <§fsenha§7>",
        ).forEach { player.sendMessage(it) }
    }

    fun takenEmailMessage(email: String, name: String): String {
        return listOf(
            "§4§lO e-mail §c§l$email §4§ljá está em uso!",
            "",
            "§eSe §bvocê já usou esse e-mail §epara criar outra conta, mas quer",
            "§eusar o nome de usuário §b${name}§e, basta entrar com o",
            "§ecomando §b/login §eusando o §bmesmo e-mail e senha§e.",
            "",
            "§eAgora, se você §dnunca jogou antes §eou esse e-mail não é seu,",
            "§eescolha §doutro endereço de e-mail §epara se cadastrar."
        ).joinToString("\n")
    }
}