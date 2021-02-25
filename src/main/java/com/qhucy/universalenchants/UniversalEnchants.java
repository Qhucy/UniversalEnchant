package com.qhucy.universalenchants;

import com.qhucy.universalenchants.config.Config;
import com.qhucy.universalenchants.config.ConfigLoadException;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Main class for UniversalEnchants (UE) that handles initialization and shutdown.
 *
 * @author Qhucy
 */
public final class UniversalEnchants
        extends JavaPlugin
{

    // If true, sends debugging statistics to console and or players during runtime.
    public static final boolean DEBUG = true;

    // Data for all values in configuration files.
    private Config config;

    // Data for basic information about this plugin.
    private PluginInfo pluginInfo;

    @Override
    public final void onEnable()
    {
        loadConfig();

        pluginInfo = new PluginInfo(getDescription());

        // utility classes

        // events
        // commands

        // scheduler

        // load data

        // enabled message
    }

    /**
     * Loads all configuration files and their values.
     * Disables the plugin if unable to load or create a configuration file.
     */
    private void loadConfig()
    {
        try
        {
            config = new Config(this);

            getLogger().info("Loaded existing values from configuration files.");
        }
        catch (final ConfigLoadException | IOException exception)
        {
            getLogger().severe("Unable to load or create configuration files.");
            // Disable the plugin to notify a server administrator of a severe error.
            setPluginFailure();

            exception.printStackTrace();
        }
    }

    @Override
    public final void onDisable()
    {
        // save data

        // disable message
    }

    /**
     * Logs a long formatted message to console.
     *
     * @param logLevel The level severity of the log.
     * @param messages The array of log messages that gets joined by a newline character.
     */
    private void formattedLogMessage(@NonNull final Level logLevel, @NonNull final String... messages)
    {
        getLogger().log(logLevel, "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n\n" + String.join("\n", messages) + "\n\n" +
                "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
    }

    /**
     * Sends a severe error message to the console and disables the plugin.
     */
    private void setPluginFailure()
    {
        formattedLogMessage(Level.SEVERE, "A SEVERE PLUGIN ERROR HAS OCCURRED!\n",
                "Read the above log messages in " + "order to understand and solve the problem.", "Then, restart the "
                        + "server in order to see if the issue has been resolved.");

        getServer().getPluginManager().disablePlugin(this);
    }

}