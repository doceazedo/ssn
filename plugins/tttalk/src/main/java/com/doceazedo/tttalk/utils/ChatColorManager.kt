package com.doceazedo.tttalk.utils

import com.doceazedo.tttalk.Tttalk
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.util.*

object ChatColorManager {
    enum class Role {
        USER, DONATOR, ADMIN
    }

    val allowedColors = listOf('0', '1', '2', '3', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
    private val defaultColors = mapOf(
        Role.USER to 'f',
        Role.DONATOR to '6',
        Role.ADMIN to '4'
    )

    private val file = File(Tttalk.instance.dataFolder, "colored-names.yml")
    private val config = YamlConfiguration.loadConfiguration(file)
    private val coloredNames = hashMapOf<UUID, String>()

    private fun getRole(player: Player): Role {
        if (player.hasPermission("tttalk.admin")) return Role.ADMIN
        if (player.hasPermission("tttalk.donator")) return Role.DONATOR
        return Role.USER
    }

    fun hasPermission(player: Player): Boolean {
        val role = getRole(player)
        return role in listOf(Role.DONATOR, Role.ADMIN)
    }

    fun loadNameColor(player: Player) {
        if (!hasPermission(player)) return
        val nameColor = config.getString("players.${player.uniqueId}")
        if (nameColor != null) coloredNames[player.uniqueId] = nameColor
    }

    fun updateNameColor(player: Player, color: Char): Boolean {
        if (!hasPermission(player)) return false

        val defaultColor = defaultColors[getRole(player)]
        if (color !in allowedColors && color != defaultColor) return false

        if (color == defaultColor) {
            coloredNames.remove(player.uniqueId)
        } else {
            coloredNames[player.uniqueId] = color.toString()
        }
        return true
    }

    fun saveNameColor(player: Player) {
        if (coloredNames[player.uniqueId] == null) return
        config.set("players.${player.uniqueId}", coloredNames[player.uniqueId])
        config.save(file)
        coloredNames.remove(player.uniqueId)
    }

    fun getNameColor(player: Player): String {
        return coloredNames[player.uniqueId] ?: defaultColors[getRole(player)].toString()
    }

    fun getColoredMessage(message: String, sender: Player): String {
        if (!hasPermission(sender)) return message
        return ChatColor.translateAlternateColorCodes('&', message)
    }
}