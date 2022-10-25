package com.doceazedo.lagosta.utils

import com.doceazedo.lagosta.Lagosta
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

object Teleport {
    private val world: String = Lagosta.instance.config.getString("world")
    private val minX: Int = Lagosta.instance.config.getInt("minX")
    private val minZ: Int = Lagosta.instance.config.getInt("minZ")
    private val maxX: Int = Lagosta.instance.config.getInt("maxX")
    private val maxZ: Int = Lagosta.instance.config.getInt("maxZ")

    suspend fun getRandomLocation(): Location {
        val x: Int = (minX..maxX).random()
        val z: Int = (minZ..maxZ).random()
        val y = 0

        val w: World = Bukkit.getServer().getWorld(world)
        val loc = Location(w, x.toDouble(), y.toDouble(), z.toDouble())

        val chunk: Chunk = loc.chunk
        if (!chunk.isLoaded) chunk.load(true)

        loc.y = w.getHighestBlockYAt(loc).toDouble()
        val floor: Block = loc.block.getRelative(BlockFace.DOWN)

        if (!floor.type.isSolid) return getRandomLocation()

        return loc
    }

    suspend fun teleportToRandomLocation(player: Player) {
        val loc = getRandomLocation()
        player.teleport(loc)
    }
}