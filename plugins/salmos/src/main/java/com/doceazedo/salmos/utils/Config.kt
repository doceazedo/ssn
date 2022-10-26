package com.doceazedo.salmos.utils

import com.doceazedo.salmos.Salmos
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


object Config {
    private val dataFolder: File = Salmos.instance.dataFolder

    fun saveDefaultConfig() {
        // Create plugin config folder if it doesn't exist
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }
        val configFile = File(dataFolder, "config.yml")

        // Copy default config if it doesn't exist
        if (!configFile.exists()) {
            val outputStream = FileOutputStream(configFile)
            val input: InputStream = Salmos.instance.getResourceAsStream("config.yml")
            input.transferTo(outputStream)
        }

        // Load config file
        Salmos.config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(File(dataFolder, "config.yml"))
    }
}