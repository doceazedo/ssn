package com.doceazedo.tttalk.utils

import com.doceazedo.tttalk.Tttalk
import org.bukkit.entity.Player

fun sendPM(sender: Player, recipient: Player, message: String) {
    // Send messages
    recipient.sendMessage("§5${sender.displayName} diz: §d$message")
    sender.sendMessage("§5Para ${recipient.displayName}: §d$message")

    // Save last chatters
    Tttalk.lastChatters[sender.uniqueId] = recipient.uniqueId
    Tttalk.lastChatters[recipient.uniqueId] = sender.uniqueId
}