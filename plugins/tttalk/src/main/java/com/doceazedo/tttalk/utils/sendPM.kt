package com.doceazedo.tttalk.utils

import com.doceazedo.tttalk.Tttalk
import org.bukkit.entity.Player

fun sendPM(sender: Player, recipient: Player, message: String) {
    // Sender message
    sender.sendMessage("§5Para ${recipient.displayName}: §d$message")
    Tttalk.lastChatters[sender.uniqueId] = recipient.uniqueId

    // Recipient message
    if (!IgnoredManager.isIgnoringPlayer(recipient.uniqueId, sender.uniqueId)) {
        recipient.sendMessage("§5${sender.displayName} diz: §d$message")
        Tttalk.lastChatters[recipient.uniqueId] = sender.uniqueId
    }
}