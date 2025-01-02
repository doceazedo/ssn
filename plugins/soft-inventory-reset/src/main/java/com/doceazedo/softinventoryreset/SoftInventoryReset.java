package com.doceazedo.softinventoryreset;

import com.doceazedo.softinventoryreset.commands.SoftInventoryResetCmd;
import com.doceazedo.softinventoryreset.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class SoftInventoryReset extends JavaPlugin {
    private static SoftInventoryReset instance;
    private final Set<String> playersWithResetInventory = new HashSet<>();
    private final Logger logger = getLogger();
    private File playersFile;
    private FileConfiguration playersConfig;

    @Override
    public void onEnable() {
        instance = this;
        createPlayersFile();
        loadPlayersWithResetInventory();
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!playersWithResetInventory.contains(player.getName())) {
                SoftInventoryResetCmd.resetPlayerInventory(player);
                playersWithResetInventory.add(player.getName());
            }
        }
    }

    @Override
    public void onDisable() {
        savePlayersWithResetInventory();
    }

    public static SoftInventoryReset getInstance() {
        return instance;
    }

    public Set<String> getPlayersWithResetInventory() {
        return playersWithResetInventory;
    }

    private void createPlayersFile() {
        playersFile = new File(getDataFolder(), "players.yml");
        if (!playersFile.exists()) {
            playersFile.getParentFile().mkdirs();
            try {
                if (playersFile.createNewFile()) {
                    logger.info("players.yml file created successfully.");
                } else {
                    logger.warning("Failed to create players.yml file.");
                }
            } catch (IOException e) {
                logger.severe("Could not create players.yml: " + e.getMessage());
            }
        }
        playersConfig = YamlConfiguration.loadConfiguration(playersFile);
    }

    private void loadPlayersWithResetInventory() {
        playersConfig.getStringList("playersWithResetInventory").forEach(playersWithResetInventory::add);
        logger.info("Loaded players with reset inventory: " + playersWithResetInventory);
    }

    public void savePlayersWithResetInventory() {
        playersConfig.set("playersWithResetInventory", new ArrayList<>(playersWithResetInventory));
        try {
            playersConfig.save(playersFile);
            logger.info("Saved players with reset inventory: " + playersWithResetInventory);
        } catch (IOException e) {
            logger.severe("Could not save players.yml: " + e.getMessage());
        }
    }
}