package com.doceazedo.catraca.events

import com.doceazedo.catraca.Catraca
import com.doceazedo.catraca.gatekeeper.Flows.awaitFlowChange
import com.doceazedo.catraca.gatekeeper.Flows.createFlow
import com.doceazedo.catraca.gatekeeper.Grants.getGrantByKey
import com.doceazedo.catraca.gatekeeper.Grants.isUserGranted
import com.doceazedo.catraca.utils.Reason
import com.doceazedo.catraca.utils.kickPlayer
import com.doceazedo.catraca.utils.sendPlayer
import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoin : Listener {
    private val spawn = Catraca.instance.config.get("spawn") as Location

    @EventHandler
    suspend fun onPlayerJoin(e: PlayerJoinEvent) {
        Catraca.instance.launch {
            e.player.teleport(spawn)

            // TODO: check if username is registered

            // check if user is granted already
            val previousGrant = isUserGranted("", e.player)
            if (previousGrant == true) sendPlayer(e.player)
            if (previousGrant == false) return@launch kickPlayer(e.player, Reason.PROHIBITED)

            // create flow and await for a response
            // if nothing is returned, the flow expired
            val flow = createFlow(e.player)
            val updatedFlow = awaitFlowChange(flow.code, e.player) ?: return@launch kickPlayer(e.player, Reason.EXPIRED_FLOW)

            // check if a grant was created for this flow
            if (updatedFlow.grantKey == null) return@launch kickPlayer(e.player, Reason.UNAUTHORIZED)

            // check if grant authorizes the user or not
            val isGranted = getGrantByKey(updatedFlow.grantKey) ?: return@launch kickPlayer(e.player, Reason.UNAUTHORIZED)
            if (!isGranted) return@launch kickPlayer(e.player, Reason.PROHIBITED)

            // if the user wasn't kicked until this point, take them to the main server
            sendPlayer(e.player)
        }
    }
}