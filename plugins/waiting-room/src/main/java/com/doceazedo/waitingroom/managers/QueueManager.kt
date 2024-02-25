package com.doceazedo.waitingroom.managers

import com.doceazedo.waitingroom.WaitingRoom
import com.doceazedo.waitingroom.utils.PlayerCount
import com.doceazedo.waitingroom.utils.formatDuration
import com.doceazedo.waitingroom.utils.sendPlayer
import com.github.shynixn.mccoroutine.bukkit.launch
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

object QueueManager {
    private var priorityQueue = mutableListOf<UUID>()
    private var regularQueue = mutableListOf<UUID>()

    fun enqueue(player: Player) {
        if (!PlayerCount.isFull()) {
            WaitingRoom.intance.launch {
                delay(1000)
                sendPlayer(player)
            }
            return
        }

        player.sendMessage(" ")
        player.sendMessage("§eO servidor está §clotado§e!")


        if (player.hasPermission("waitingroom.skip")) {
            priorityQueue.add(0, player.uniqueId)
            player.sendMessage("§eVocê §6furou fila §ee poderá jogar primeiro.")
        } else if (player.hasPermission("waitingroom.priority")) {
            priorityQueue.add(player.uniqueId)
            player.sendMessage("§eVocê tem §6prioridade§e, obrigado por apoiar o SSN.")
        } else {
            regularQueue.add(player.uniqueId)
            player.sendMessage("§eVocê será conectado em breve, por favor aguarde...")
        }
    }

    fun dequeue(uuid: UUID) {
        priorityQueue = priorityQueue.filter { it != uuid }.toMutableList()
        regularQueue = regularQueue.filter { it != uuid }.toMutableList()
    }

    fun runQueue() {
        val queue = priorityQueue + regularQueue
        queue.forEachIndexed { i, uuid ->
            val player = Bukkit.getPlayer(uuid) ?: return
            val pos = i + 1
            player.sendMessage(" ")
            player.sendMessage("§eSua posição na fila: §6$pos")
            player.sendMessage("§eTempo de espera estimado: §6${formatDuration(pos * 45.0)}")
        }

        if (PlayerCount.isFull()) return

        val availableSlots = PlayerCount.getAvailableSlots()
        queue.take(availableSlots).forEach {
            val player = Bukkit.getPlayer(it) ?: return
            dequeue(it)
            sendPlayer(player)
        }
    }
}