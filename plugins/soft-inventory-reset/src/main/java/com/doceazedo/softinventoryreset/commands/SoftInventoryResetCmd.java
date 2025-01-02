package com.doceazedo.softinventoryreset.commands;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.logging.Logger;

public class SoftInventoryResetCmd {
    private static final Logger logger = Logger.getLogger(SoftInventoryResetCmd.class.getName());

    public static void resetPlayerInventory(Player player) {
        logger.info("Resetting inventory for player: " + player.getName());
        checkContainer(player.getInventory());
        checkContainer(player.getEnderChest());
    }

    private static boolean checkContainer(Inventory inventory) {
        boolean modified = false;

        ItemStack[] contents = inventory.getContents();
        logger.info("Checking container with " + contents.length + " items.");

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) {
                continue;
            }

            String typeName = item.getType().name();
            logger.info("Checking item: " + typeName);

            if (typeName.equals("END_CRYSTAL") ||
                    typeName.equals("EXPERIENCE_BOTTLE") ||
                    typeName.equals("ENCHANTED_GOLDEN_APPLE") ||
                    typeName.equals("ENDER_CHEST") ||
                    typeName.equals("TIPPED_ARROW")) {
                item.setAmount(1);
                modified = true;
                logger.info("Modified item: " + typeName);
            } else if (typeName.equals("TOTEM_OF_UNDYING") || typeName.equals("ELYTRA")) {
                inventory.removeItem(item);
                modified = true;
                logger.info("Removed item: " + typeName);
            } else if (typeName.startsWith("NETHERITE_")) {
                item.getEnchantments().keySet().forEach(item::removeEnchantment);
                modified = true;
                logger.info("Removed enchantments from item: " + typeName);
            } else if (item.getItemMeta() instanceof BlockStateMeta) {
                BlockStateMeta itemMeta = (BlockStateMeta) item.getItemMeta();
                BlockState blockState = itemMeta.getBlockState();
                if (blockState instanceof Inventory) {
                    Inventory container = (Inventory) blockState;
                    if (checkContainer(container)) {
                        itemMeta.setBlockState(blockState);
                        item.setItemMeta(itemMeta);
                        modified = true;
                        logger.info("Modified container item: " + typeName);
                    }
                }
            }
        }
        return modified;
    }
}