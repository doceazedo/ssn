package com.doceazedo.catraca.managers

import com.doceazedo.catraca.Catraca.Companion.instance
import org.bukkit.entity.Player
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.zip.Inflater
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object CaptchaManager {
    data class CaptchaArtwork(
        val image: String,
        val answers: List<String>,
    )

    val captchas = mutableMapOf<UUID, CaptchaArtwork>()
    private var artworks = mutableListOf<CaptchaArtwork>()

    init {
        instance.config.getMapList("captchas").forEach {
            artworks.add(CaptchaArtwork(
                it["image"] as String,
                it["answers"] as List<String>,
            ))
        }
    }

    fun sendCaptcha(player: Player) {
        if (!captchas.contains(player.uniqueId)) {
            captchas[player.uniqueId] = artworks.random()
        }

        val messages = codeToColors(captchas[player.uniqueId]!!.image).toMutableList()
        messages[2] += " §6§lPROVE QUE VOCÊ NÃO É UM ROBÔ!"
        messages[4] += " §eQuase lá! Para confirmar o registro"
        messages[5] += " §eda sua nova conta, §bdigite no chat que"
        messages[6] += " §bimagem aparece no desenho ao lado§e."
        messages[7] += " §8§oExemplo: \"cachorro\""

        messages.forEach { player.sendMessage(it) }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun codeToColors(code: String): List<String> {
        val decompressedString: String
        val bytes: ByteArray = Base64.decode(code)
        val inflater = Inflater(false)
        val outputStream = ByteArrayOutputStream()
        val buffer = ByteArray(0x8000)
        inflater.setInput(bytes)
        while (!inflater.finished()) {
            val count = inflater.inflate(buffer)
            outputStream.write(buffer, 0, count)
        }
        inflater.end()
        outputStream.close()
        decompressedString = outputStream.toString("UTF8")
        return decompressedString
            .chunked(13)
            .map { row -> row.map { color -> "§$color■" } }
            .map { row -> row.joinToString(separator = "")}
    }
}