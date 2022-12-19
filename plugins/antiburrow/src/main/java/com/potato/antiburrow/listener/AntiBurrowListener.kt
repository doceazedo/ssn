package com.potato.antiburrow.listener

import com.potato.antiburrow.Main
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class AntiBurrowListener(pl: Main?) : Listener {
    init {
        Companion.pl = pl
    }

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val p = event.player
        val l = p.location
        val x = l.blockX
        val y = l.blockY
        val z = l.blockZ
        val m = l.world.getBlockAt(x, y, z).type
        if (m != Material.AIR && m.isOccluding) {
            if (pl!!.config.getBoolean("Teleport")) {
                p.teleport(l.add(0.0, 1.0, 0.0))
            }
            if (pl!!.config.getBoolean("Damage")) {
                p.damage(pl!!.config.getDouble("DamageV"))
            }
            if (pl!!.config.getBoolean("Kick")) {
                p.kickPlayer("\u00A74" + "[AntiBurrow]" + " the burrow is disabled")
            }
            if (pl!!.config.getBoolean("Alert")) {
                p.sendMessage("\u00A74" + "[AntiBurrow]" + " the burrow is disabled")
                p.playSound(l, Sound.BLOCK_GLASS_BREAK, 20f, 5f)
            }
            if (pl!!.config.getBoolean("Log")) {
                pl!!.server.consoleSender.sendMessage("\u00A74" + "[AntiBurrow]" + " a burrow was prevented, name player:" + p.name)
            }
        }
    }

    companion object {
        private var pl: Main? = null
    }
}