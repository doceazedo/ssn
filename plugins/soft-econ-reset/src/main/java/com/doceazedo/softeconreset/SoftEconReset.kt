package com.doceazedo.softeconreset

import com.doceazedo.softeconreset.commands.SoftEconResetCmd
import com.github.shynixn.mccoroutine.bukkit.setSuspendingExecutor
import org.bukkit.plugin.java.JavaPlugin

class SoftEconReset : JavaPlugin() {
    companion object {
        lateinit var instance: SoftEconReset
    }

    override fun onEnable() {
        instance = this
        getCommand("soft-econ-reset")!!.setSuspendingExecutor(SoftEconResetCmd)
    }
}