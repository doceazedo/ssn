package com.doceazedo.softeconreset.commands

import com.doceazedo.softeconreset.SoftEconReset.Companion.instance
import com.github.shynixn.mccoroutine.bukkit.SuspendingCommandExecutor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Container
import org.bukkit.block.ShulkerBox
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.loot.Lootable
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.listDirectoryEntries


object SoftEconResetCmd : SuspendingCommandExecutor {
    override suspend fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val world = Bukkit.getWorld("world") ?: return false

        val worldPath = Paths.get(instance.server.worldContainer.path, "world", "region")
        val chunkFiles = worldPath.listDirectoryEntries()

        val lastChunkIdx = -1
        checkChunk(world, chunkFiles, lastChunkIdx + 1)
        return true
    }

    private fun checkChunk (world: World, chunkFiles: List<Path>, idx: Int) {
        if (idx > chunkFiles.size - 1) {
            Bukkit.getLogger().info("No more chunks to check on world '${world.name}'!")
            return
        }

        val chunkFile = chunkFiles[idx].fileName.toString()
        val (_, x, z) = chunkFile.split(".")
        Bukkit.getLogger().info("[${idx + 1}/${chunkFiles.size}] Checking chunk $chunkFile on world '${world.name}'...")

        val chunk = world.getChunkAt(x.toInt(), z.toInt())
        chunk.tileEntities.forEach { chest ->
            if (chest is InventoryHolder) {
                Bukkit.getLogger().info("Checking inventory: ${chest.type} (${chest.location})")
                checkInventory(chest.inventory)
            }
        }
        chunk.unload()

        checkChunk(world, chunkFiles, idx + 1)
    }

    private fun checkInventory (inventory: Inventory): Inventory {
        inventory.forEach { item ->
            if (item == null) return@forEach

            // remove
            if (item.type == Material.TOTEM_OF_UNDYING) {
                Bukkit.getLogger().info("Removing: ${item.type}")
                inventory.removeItem(item)
                return@forEach
            }

            // reduce
            if (item.type in arrayOf(
                Material.END_CRYSTAL,
                Material.EXPERIENCE_BOTTLE,
                Material.ENCHANTED_GOLDEN_APPLE
            )) {
                Bukkit.getLogger().info("Reducing: ${item.type}")
                item.amount = 1
                return@forEach
            }

            // shulker box
            if (item.itemMeta is BlockStateMeta) {
                Bukkit.getLogger().info("Checking Shulker Box inside inventory")
                val itemMeta = item.itemMeta as BlockStateMeta
                val shulker = itemMeta.blockState as ShulkerBox
                shulker.inventory.contents = checkInventory(shulker.inventory).contents
                itemMeta.blockState = shulker
                item.itemMeta = itemMeta
            }
        }

        return inventory
    }
}