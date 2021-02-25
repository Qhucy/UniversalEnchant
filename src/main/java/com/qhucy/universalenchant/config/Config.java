package com.qhucy.universalenchant.config;

import com.qhucy.universalenchant.UniversalEnchant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Class that manages configuration (config) variables (vars) from config files.
 *
 * @author Qhucy
 * @since 08 Feb, 2021
 */
public final class Config
{

    @Getter(AccessLevel.PRIVATE)
    private final UniversalEnchant plugin;

    /**
     * Loads in all config variables from the config files.
     *
     * @param plugin The main instance of the plugin. Can't be null.
     *
     * @throws ConfigLoadException If unable to generate the plugin data folder or load fields from config files.
     * @throws IOException         If unable to modify or load config files.
     */
    public Config(@NonNull final UniversalEnchant plugin)
            throws ConfigLoadException, IOException
    {
        this.plugin = plugin;

        createPluginFolder();
        createConfigFiles();

        loadConfigFiles();
    }

    /**
     * Returns the plugin data folder.
     *
     * @return the plugin data folder.
     */
    private File getPluginFolder()
    {
        return getPlugin().getDataFolder();
    }

    /**
     * Creates the plugin data folder if it does not exist.
     *
     * @throws ConfigLoadException If there is an error creating the plugin data folder.
     */
    private void createPluginFolder()
            throws ConfigLoadException
    {
        final File pluginFolder = getPluginFolder();

        if (!pluginFolder.exists() && !pluginFolder.mkdirs())
        {
            throw new ConfigLoadException("Unable to create plugin data folder.");
        }
    }

    /**
     * Creates the config files for the plugin if they don't exist.
     *
     * @throws IOException         If unable to copy the default config.
     * @throws ConfigLoadException If unable to retrieve the default config.
     */
    private void createConfigFiles()
            throws IOException, ConfigLoadException
    {
        // None yet!
    }

    /**
     * Creates a config file if it doesn't exist.
     *
     * @param configName The name of the config file to create. Can't be null.
     *
     * @throws IOException         If unable to copy the default config.
     * @throws ConfigLoadException If unable to retrieve the default config.
     */
    private void createConfigFile(@NonNull final String configName)
            throws IOException, ConfigLoadException
    {
        final File configFile = new File(getPluginFolder(), configName);

        if (!configFile.exists())
        {
            try (final InputStream configResource = getPlugin().getResource(configName))
            {
                if (configResource == null)
                {
                    throw new ConfigLoadException("Unable to load '" + configName + "' " + "as a resource for the " + "default config.");
                }
                else
                {
                    // Copies the default config.
                    Files.copy(configResource, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    /**
     * Loads in all config variables from all the config files.
     *
     * @throws IOException         If unable to load a config file.
     * @throws ConfigLoadException If unable to load a field from one of the configs.
     */
    public final void loadConfigFiles()
            throws IOException, ConfigLoadException
    {
        loadMainConfig();
    }

    /**
     * Loads in all config variables from the main config file.
     *
     * @throws IOException         If unable to load the main config file.
     * @throws ConfigLoadException If unable to load a field from the main config.
     */
    private void loadMainConfig()
            throws IOException, ConfigLoadException
    {
        final File configFile = new File(getPluginFolder(), "config.yml");
        final ConfigManager configManager = new ConfigManager(configFile);

        // load values here.
    }

}