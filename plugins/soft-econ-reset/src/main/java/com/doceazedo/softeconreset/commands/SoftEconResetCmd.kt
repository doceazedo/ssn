package com.doceazedo.softeconreset.commands

import com.doceazedo.softeconreset.SoftEconReset.Companion.instance
import com.github.shynixn.mccoroutine.bukkit.SuspendingCommandExecutor
import com.github.shynixn.mccoroutine.bukkit.launch
import com.github.shynixn.mccoroutine.bukkit.minecraftDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Container
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Item
import org.bukkit.entity.ItemFrame
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.listDirectoryEntries


object SoftEconResetCmd : SuspendingCommandExecutor {
    override suspend fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val world = Bukkit.getWorld("world") ?: return false

        val worldPath = Paths.get(instance.server.worldContainer.path, "world", "region")
        val chunkFiles = worldPath.listDirectoryEntries()

        val lastChunkIdx = -1
        instance.launch {
            withContext(Dispatchers.IO) {
                //checkChunk(world, chunkFiles, lastChunkIdx + 1)
                val playerDataPath = Paths.get(instance.server.worldContainer.path, "world", "playerdata")
                val playerDataFiles = playerDataPath.listDirectoryEntries()
                checkPlayerData(world, playerDataFiles)
            }
        }

        return true
    }

    private fun checkPlayerData(world: World, playerDataFiles: List<Path>) {
        var i = 0
        var total = playerDataFiles.size

        playerDataFiles.forEach { playerDataFile ->
            i += 1

            if (playerDataFile.endsWith(".dat_old")) {
                return
            }

            val playerUuid = try {
                UUID.fromString(playerDataFile.toString().split("/").last().substringBefore(".dat"))
            } catch (e: IllegalArgumentException) {
                UUID.fromString("00000000-0000-0000-0000-000000000000")
            }
            Bukkit.getLogger().info("[$i/$total] Checking UUID '${playerUuid}'!")
            val player = Bukkit.getPlayer(playerUuid)

            if (player != null) {
                Bukkit.getLogger().info("Checking player '${player.name}'!")

                checkInventory(player.inventory)
                checkInventory(player.enderChest)
            }
        }
    }

    private fun checkChunk(world: World, chunkFiles: List<Path>, idx: Int) {
        if (idx > chunkFiles.size - 1) {
            Bukkit.getLogger().info("No more chunks to check on world '${world.name}'!")
            return
        }

        val chunkFile = chunkFiles[idx].fileName.toString()
        val (_, x, z) = chunkFile.split(".")
        Bukkit.getLogger().info("[${idx + 1}/${chunkFiles.size}] Checking chunk $chunkFile on world '${world.name}'...")

        instance.launch {
            val chunk = world.getChunkAt(x.toInt(), z.toInt())

            chunk.tileEntities.forEach { chest ->
                if (chest is InventoryHolder) {
                    Bukkit.getLogger().info("Checking inventory: ${chest.type} (${chest.location})")
                    checkInventory(chest.inventory)
                }
            }

            chunk.entities.forEach { entity ->
                if (entity is InventoryHolder) {
                    Bukkit.getLogger().info("Checking inventory of entity: ${entity.type} (${entity.location})")
                    checkInventory(entity.inventory)
                } else if (entity is ItemFrame) {
                    Bukkit.getLogger().info("Checking item of item frame: ${entity.type} (${entity.location})")
                    checkItemFrame(entity)
                }
            }
            chunk.unload()

            instance.launch {
                withContext(Dispatchers.IO) {
                    checkChunk(world, chunkFiles, idx + 1)
                }
            }
        }
    }

    private fun checkItemFrame(itemFrame: ItemFrame) {
        //FODASE KKKKKKKKKK
        if (itemFrame.item.itemMeta is BlockStateMeta) {
            Bukkit.getLogger().info("Checking Shulker Box inside item frame")
            val itemMeta = itemFrame.item.itemMeta as BlockStateMeta
            if (itemMeta.blockState is Container) {
                itemFrame.setItem(null)
            }
        }
    }

    private fun checkInventory(inventory: Inventory): Inventory {
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
                    Material.ENCHANTED_GOLDEN_APPLE,
                    Material.ENDER_CHEST,
                    Material.TIPPED_ARROW
                )
            ) {
                Bukkit.getLogger().info("Reducing: ${item.type}")
                item.amount = 1
                return@forEach
            }

            //remove enchantments
            if (item.type in arrayOf(
                    Material.NETHERITE_AXE,
                    Material.NETHERITE_BLOCK,
                    Material.NETHERITE_BOOTS,
                    Material.NETHERITE_CHESTPLATE,
                    Material.NETHERITE_HELMET,
                    Material.NETHERITE_HOE,
                    Material.NETHERITE_INGOT,
                    Material.NETHERITE_LEGGINGS,
                    Material.NETHERITE_PICKAXE,
                    Material.NETHERITE_SCRAP,
                    Material.NETHERITE_SHOVEL,
                    Material.NETHERITE_SWORD,
                    Material.ELYTRA
                )
            ) {
                Bukkit.getLogger().info("Removing enchantments: ${item.type}")
                item.removeEnchantments()
                return@forEach
            }

            // shulker box
            if (item.itemMeta is BlockStateMeta) {
                Bukkit.getLogger().info("Checking Shulker Box inside inventory")
                val itemMeta = item.itemMeta as BlockStateMeta
                if (itemMeta.blockState is Container) {
                    val container = itemMeta.blockState as Container
                    container.inventory.contents = checkInventory(container.inventory).contents
                    itemMeta.blockState = container
                }
                item.itemMeta = itemMeta
            }
        }

        return inventory
    }
}