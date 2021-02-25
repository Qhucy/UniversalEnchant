package com.qhucy.universalenchant;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PluginInfo Class Testing")
final class PluginInfoTest
{

    private static PluginDescriptionFile dummyPluginDescriptionFile;
    private PluginInfo pluginInfo;

    @BeforeAll
    static void setUpAll()
    {
        try
        {
            dummyPluginDescriptionFile = new PluginDescriptionFile(new FileInputStream("src/test/java/com/qhucy" +
                    "/universalenchant/dummy_plugin.yml"));
        }
        catch (InvalidDescriptionException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp()
    {
        pluginInfo = new PluginInfo(dummyPluginDescriptionFile);
    }

    @Test
    @DisplayName("Getting Plugin Name")
    void getName()
    {
        assertEquals(pluginInfo.getName(), "name");
    }

    @Test
    @DisplayName("Getting Plugin Version")
    void getVersion()
    {
        assertEquals(pluginInfo.getVersion(), "version");
    }

    @Test
    @DisplayName("Getting Plugin Authors")
    void getAuthors()
    {
        final String[] authors = pluginInfo.getAuthors();

        assertEquals(authors.length, 1);
        assertEquals(authors[0], "author");
    }

}