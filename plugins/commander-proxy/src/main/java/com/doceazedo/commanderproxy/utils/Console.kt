package com.doceazedo.commanderproxy.utils

import com.doceazedo.commanderproxy.CommanderProxy

object Console {
    private val console = CommanderProxy.instance.proxy.console

    fun exec(command: String) {
        CommanderProxy.instance.proxy.pluginManager.dispatchCommand(console, command)
    }
}