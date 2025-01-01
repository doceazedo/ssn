package com.doceazedo.softeconreset;

import com.doceazedo.softeconreset.commands.SoftEconResetCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class SoftEconReset extends JavaPlugin {
    public static SoftEconReset instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("soft-econ-reset").setExecutor(new SoftEconResetCmd());
    }
}