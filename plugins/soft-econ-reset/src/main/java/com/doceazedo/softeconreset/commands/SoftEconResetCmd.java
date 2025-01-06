package com.doceazedo.softeconreset.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.logging.Logger;

public class SoftEconResetCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Logger logger = Bukkit.getLogger();
        logger.info("Command received: " + label);

        for (World world : Bukkit.getWorlds()) {
            logger.info("Checking world: " + world.getName());
            for (var chunk : world.getLoadedChunks()) {
                checkChunk(chunk);
            }
        }

        return true;
    }

    private void checkChunk(org.bukkit.Chunk chunk) {
        Logger logger = Bukkit.getLogger();
        logger.info("Checking chunk at (" + chunk.getX() + ", " + chunk.getZ() + ")");

        for (var tileEntity : chunk.getTileEntities()) {
            if (tileEntity instanceof Container) {
                checkInventory(((Container) tileEntity).getInventory());
            }
        }

        for (Entity entity : chunk.getEntities()) {
            if (entity instanceof ItemFrame) {
                checkItemFrame((ItemFrame) entity);
            }
        }
    }

    private boolean checkContainer(Inventory inventory) {
        Logger logger = Bukkit.getLogger();
        boolean modified = false;

        ItemStack[] contents = inventory.getContents();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) {
                continue;
            }

            String typeName = item.getType().name();
            if (typeName.equals("END_CRYSTAL") ||
                    typeName.equals("EXPERIENCE_BOTTLE") ||
                    typeName.equals("ENCHANTED_GOLDEN_APPLE") ||
                    typeName.equals("ENDER_CHEST") ||
                    typeName.equals("TIPPED_ARROW")) {
                logger.info("Slot " + i + ": Reducing: " + typeName);
                item.setAmount(1);
                modified = true;
            } else if (typeName.equals("TOTEM_OF_UNDYING")) {
                inventory.removeItem(item);
                modified = true;
            } else if (typeName.equals("NETHERITE_AXE") ||
                    typeName.equals("NETHERITE_BLOCK") ||
                    typeName.equals("NETHERITE_BOOTS") ||
                    typeName.equals("NETHERITE_CHESTPLATE") ||
                    typeName.equals("NETHERITE_HELMET") ||
                    typeName.equals("NETHERITE_HOE") ||
                    typeName.equals("NETHERITE_INGOT") ||
                    typeName.equals("NETHERITE_LEGGINGS") ||
                    typeName.equals("NETHERITE_PICKAXE") ||
                    typeName.equals("NETHERITE_SCRAP") ||
                    typeName.equals("NETHERITE_SHOVEL") ||
                    typeName.equals("NETHERITE_SWORD")) {
                logger.info("Slot " + i + ": Removing enchantments: " + typeName);
                final int slotIndex = i; // Make a copy of the variable
                item.getEnchantments().keySet().forEach(enchantment -> {
                    logger.info("Slot " + slotIndex + ": Removing enchantment: " + enchantment);
                    item.removeEnchantment(enchantment);
                });
                modified = true;
            } else if (typeName.equals("ELYTRA")) {
                logger.info("Slot " + i + ": Removing Elytra");
                inventory.removeItem(item);
                modified = true;
            } else if (item.getItemMeta() instanceof BlockStateMeta) {
                BlockStateMeta itemMeta = (BlockStateMeta) item.getItemMeta();
                if (itemMeta.getBlockState() instanceof Container) {
                    Container container = (Container) itemMeta.getBlockState();
                    if (checkContainer(container.getInventory())) {
                        itemMeta.setBlockState(container);
                        item.setItemMeta(itemMeta);
                        modified = true;
                    }
                }
            }
        }
        return modified;
    }

    private void checkInventory(Inventory inventory) {
        Logger logger = Bukkit.getLogger();
        logger.info("Checking inventory...");

        if (inventory instanceof DoubleChestInventory) {
            DoubleChestInventory doubleChestInventory = (DoubleChestInventory) inventory;
            boolean leftModified = checkContainer(doubleChestInventory.getLeftSide());
            boolean rightModified = checkContainer(doubleChestInventory.getRightSide());
            if (leftModified || rightModified) {
                logger.info("Double chest inventory modified.");
            }
            return;
        }

        if (checkContainer(inventory)) {
            logger.info("Inventory modified.");
        }
        logger.info("Finished checking inventory.");
    }

    private void checkItemFrame(ItemFrame itemFrame) {
        // No changes needed for item frames
    }
}