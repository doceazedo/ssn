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
                player.sendMessage("§5Por favor, aguarde...")
                delay(500)
                sendPlayer(player)
            }
            return
        }

        player.sendMessage(" ")
        player.sendMessage("§eO servidor está §clotado§e!")

        var pos = 1
        var isPriority = false
        if (player.hasPermission("waitingroom.skip")) {
            priorityQueue.add(0, player.uniqueId)
            isPriority = true
            player.sendMessage("§eVocê §6furou fila §ee poderá jogar primeiro.")
        } else if (player.hasPermission("waitingroom.priority")) {
            priorityQueue.add(player.uniqueId)
            isPriority = true
            pos = priorityQueue.size
            player.sendMessage("§eVocê tem §6prioridade§e, obrigado por apoiar o SSN.")
        } else {
            regularQueue.add(player.uniqueId)
            pos = priorityQueue.size + regularQueue.size
            player.sendMessage("§eVocê será conectado em breve, por favor aguarde...")
        }

        sendQueuePosition(player, pos, isPriority)
    }

    fun dequeue(uuid: UUID) {
        priorityQueue = priorityQueue.filter { it != uuid }.toMutableList()
        regularQueue = regularQueue.filter { it != uuid }.toMutableList()
    }

    fun runQueue() {
        priorityQueue.forEachIndexed { i, uuid ->
            sendQueuePosition(uuid, i + 1, true)
        }
        regularQueue.forEachIndexed { i, uuid ->
            sendQueuePosition(uuid, priorityQueue.size + i + 1, false)
        }

        if (PlayerCount.isFull()) return

        val queue = priorityQueue + regularQueue
        val availableSlots = PlayerCount.getAvailableSlots()
        queue.take(availableSlots).forEach {
            val player = Bukkit.getPlayer(it) ?: return
            dequeue(it)
            sendPlayer(player)
        }
    }

    private fun sendQueuePosition(uuid: UUID, pos: Int, isPriority: Boolean) {
        val player = Bukkit.getPlayer(uuid) ?: return
        sendQueuePosition(player, pos, isPriority)
    }

    private fun sendQueuePosition(player: Player, pos: Int, isPriority: Boolean) {
        val posMessage = "§eSua posição na fila: §6$pos"
        val subtitle = if (isPriority) "§6§lFILA PRIORITÁRIA" else ""

        repeat(8) { player.sendMessage(" ") }
        player.sendMessage(posMessage)
        player.sendMessage("§eTempo de espera estimado: §6${formatDuration(pos * 45.0)}")
        player.sendTitle(posMessage, subtitle, 0, 101, 0) // 5sec + 1tick
    }
}