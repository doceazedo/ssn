package com.doceazedo.softinventoryreset.listeners;

import com.doceazedo.softinventoryreset.SoftInventoryReset;
import com.doceazedo.softinventoryreset.commands.SoftInventoryResetCmd;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

public class PlayerJoinListener implements Listener {
    private final Set<String> playersWithResetInventory = SoftInventoryReset.getInstance().getPlayersWithResetInventory();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        if (!playersWithResetInventory.contains(playerName)) {
            SoftInventoryResetCmd.resetPlayerInventory(event.getPlayer());
            playersWithResetInventory.add(playerName);
            SoftInventoryReset.getInstance().savePlayersWithResetInventory();
        }
    }
}