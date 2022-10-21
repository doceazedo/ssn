package com.doceazedo.catraca.events

import com.doceazedo.catraca.Catraca
import com.doceazedo.catraca.gatekeeper.Flows.checkFlow
import com.doceazedo.catraca.gatekeeper.Flows.createFlow
import com.doceazedo.catraca.utils.sendPlayer
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoin : Listener {
    @EventHandler
    suspend fun onPlayerJoin(e: PlayerJoinEvent) {
        Catraca.instance.launch {
            // TODO: check if username is registered

            // TODO: check if player is already granted

            val flow = createFlow(e.player)
            
            val updatedFlow = checkFlow(flow.code, e.player)
            e.player.sendMessage("Updated flow ${updatedFlow?.code}: grantKey is ${updatedFlow?.grantKey}")

            // TODO: check if updatedFlow is null, kick player because it expired

            // TODO: get grant, check if it is authorized, kick player if it isn't

            // TODO: send player to targetServer
            sendPlayer(e.player)
        }
    }
}