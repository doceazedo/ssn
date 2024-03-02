package com.doceazedo.inflightconn

import com.doceazedo.inflightconn.events.PlayerLogin
import com.doceazedo.inflightconn.utils.Config
import com.github.shynixn.mccoroutine.bungeecord.SuspendingPlugin
import com.github.shynixn.mccoroutine.bungeecord.registerSuspendingListener
import net.md_5.bungee.config.Configuration

class InFlightConn : SuspendingPlugin() {
    companion object {
        lateinit var instance: InFlightConn
        lateinit var config: Configuration
    }

    override suspend fun onEnableAsync() {
        instance = this
        config = Config.loadConfig()
        proxy.pluginManager.registerSuspendingListener(this, PlayerLogin)
    }

    override suspend fun onDisableAsync() {
        // Plugin shutdown logic
    }
}