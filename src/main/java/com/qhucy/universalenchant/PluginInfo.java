package com.qhucy.universalenchant;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * Class that holds basic information about this plugin.
 *
 * @author Qhucy
 * @since 08 Feb, 2021
 */
@Getter
public final class PluginInfo
{

    private final String name;
    private final String version;
    private final String[] authors;

    /**
     * Loads basic information about this plugin from the plugin description file (plugin.yml).
     *
     * @param pluginDescriptionFile The plugin description file. Can't be null.
     */
    public PluginInfo(@NonNull final PluginDescriptionFile pluginDescriptionFile)
    {
        this.name = pluginDescriptionFile.getName();
        this.version = pluginDescriptionFile.getVersion();
        this.authors = pluginDescriptionFile.getAuthors().toArray(new String[0]);
    }

}