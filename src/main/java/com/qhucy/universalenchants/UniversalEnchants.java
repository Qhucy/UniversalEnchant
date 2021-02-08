package com.qhucy.universalenchants;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for UniversalEnchants (UE) that initializes the foundation of the plugin.
 *
 * @author Qhucy
 * @since 08 Feb, 2021
 */
public final class UniversalEnchants extends JavaPlugin
{

    public static final boolean DEBUG = true;

    // Data for basic information about this plugin.
    private PluginInfo pluginInfo;

    @Override
    public final void onEnable()
    {
        pluginInfo = new PluginInfo(getDescription());
    }

    @Override
    public final void onDisable()
    {

    }

}