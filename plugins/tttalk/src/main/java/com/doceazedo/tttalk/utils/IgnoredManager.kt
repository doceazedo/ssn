package com.doceazedo.tttalk.utils

import com.doceazedo.tttalk.Tttalk
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

object IgnoredManager {
    private val file = File(Tttalk.instance.dataFolder, "ignored.yml")
    private val config = YamlConfiguration.loadConfiguration(file)
    private val ignoredPlayers = hashMapOf<UUID, MutableList<UUID>>()

    fun loadIgnoredPlayers(uuid: UUID) {
        ignoredPlayers[uuid] = config.getStringList("players.$uuid")?.map { UUID.fromString(it) }?.toMutableList() ?: mutableListOf()
    }

    fun saveIgnoredPlayers(uuid: UUID) {
        if (ignoredPlayers[uuid] == null) return
        config.set("players.$uuid", ignoredPlayers[uuid]!!.map { it.toString() }.toList())
        config.save(file)
        ignoredPlayers.remove(uuid)
    }

    fun saveAll() {
        ignoredPlayers.forEach { (uuid, list) ->
            config.set("players.$uuid", list.map { it.toString() }.toList())
        }
        config.save(file)
    }

    fun getIgnoredPlayers(uuid: UUID): MutableList<UUID> {
        return ignoredPlayers.computeIfAbsent(uuid) { mutableListOf() }
    }

    fun ignorePlayer(uuid: UUID, ignored: UUID) {
        getIgnoredPlayers(uuid).add(ignored)
    }

    fun stopIgnoringPlayer(uuid: UUID, ignored: UUID) {
        getIgnoredPlayers(uuid).remove(ignored)
    }

    fun isIgnoringPlayer(uuid: UUID, ignored: UUID): Boolean {
        return ignored in getIgnoredPlayers(uuid)
    }
}