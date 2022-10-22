package com.doceazedo.catraca.utils

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

fun createBossBar(title: String, color: BarColor, player: Player): BossBar {
    val bar = Bukkit.getServer().createBossBar(title, color, BarStyle.SOLID)
    bar.isVisible = true
    bar.addPlayer(player)
    bar.progress = 1.0
    return bar
}