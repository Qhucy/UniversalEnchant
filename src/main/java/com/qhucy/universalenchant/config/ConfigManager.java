package com.qhucy.universalenchant.config;

import com.qhucy.universalenchant.util.MessageManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages retrieving values from a configuration (config) file.
 *
 * @author Qhucy
 * @see Config
 */
public final class ConfigManager
{

    /**
     * TODO
     * make list / array versions of all data types and fix all errosr
     */

    // The FileConfiguration object from config that retrieves values from fields.
    @Getter(AccessLevel.PRIVATE)
    private final FileConfiguration fileConfiguration;

    /**
     * Loads the FileConfiguration object from the config file.
     *
     * @param configFile The config file. Can't be null.
     */
    public ConfigManager(@NonNull final File configFile)
    {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Returns if a field exists in the config.
     *
     * @param field The field in the config. Can't be null.
     *
     * @return If the field exists in the config.
     */
    public final boolean containsField(@NonNull final String field)
    {
        return getFileConfiguration().contains(field);
    }

    /**
     * Returns if a field is a certain type.
     *
     * @param field The field. Can't be null.
     * @param type  The type. Can't be null.
     *
     * @return If the field is the given type.
     */
    public final boolean fieldIsType(@NonNull final String field, @NonNull final Class<?> type)
    {
        final Object object = getFileConfiguration().get(field);

        return object != null && object.getClass().equals(type);
    }

    /**
     * Returns if a given field is a list of a given type.
     *
     * @param field The field. Can't be null.
     * @param type  The type. Can't be null.
     *
     * @return If the field is a list of the type.
     */
    public final boolean fieldIsListOfType(@NonNull final String field, @NonNull final Class<?> type)
    {
        if (containsField(field))
        {
            Object object = getFileConfiguration().get(field);

            if (object instanceof List<?>)
            {
                for (Object obj : (List<?>) object)
                {
                    if (!obj.getClass().equals(type))
                    {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Returns if a given field is an array of a given type.
     *
     * @param field The field. Can't be null.
     * @param type  The type. Can't be null.
     *
     * @return If the field is an array of the type.
     */
    public final boolean fieldIsArrayOfType(@NonNull final String field, @NonNull final Class<?> type)
    {
        final Object object = getFileConfiguration().get(field);

        if (object != null)
        {
            if (object.getClass().isArray())
            {
                for (final Object obj : (Object[]) object)
                {
                    if (!obj.getClass().equals(type))
                    {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves a boolean from a field in config.
     *
     * @param field        The field. Can't be null.
     * @param defaultValue The value to return if the field doesn't exist.
     *
     * @return The boolean from the field in config or the default value if the field doesn't exist.
     */
    public final boolean getBoolean(@NonNull final String field, final boolean defaultValue)
    {
        return getFileConfiguration().getBoolean(field, defaultValue);
    }

    /**
     * Retrieves a boolean from a field in config.
     *
     * @param field The field. Can't be null.
     *
     * @return The boolean from the field in config.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final boolean getBoolean(@NonNull final String field)
            throws ConfigLoadException
    {
        if (!fieldIsType(field, Boolean.class))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config or is not a boolean.");
        }
        else
        {
            return getFileConfiguration().getBoolean(field);
        }
    }

    /**
     * Retrieves a boolean list from a field in config.
     *
     * @param field        The field. Can't be null.
     * @param defaultValue The value to return if the field doesn't exist. Can be null.
     *
     * @return The boolean list from the field in config or the default value if the field doesn't exist.
     */
    public final List<Boolean> getBooleanList(@NonNull final String field, final List<Boolean> defaultValue)
    {
        return !fieldIsListOfType(field, Boolean.class) ? defaultValue : getFileConfiguration().getBooleanList(field);
    }

    /**
     * Retrieves a boolean list from a field in config.
     *
     * @param field The field. Can't be null.
     *
     * @return The boolean list from the field in the config file.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final List<Boolean> getBooleanList(@NonNull final String field)
            throws ConfigLoadException
    {
        if (!fieldIsListOfType(field, Boolean.class))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config or is not a boolean list.");
        }
        else
        {
            return getFileConfiguration().getBooleanList(field);
        }
    }

    /**
     * Retrieves a string array from a field in config.
     *
     * @param field                The field. Can't be null.
     * @param defaultValue         The value to return if the field doesn't exist. Can be null.
     * @param replacementVariables The variables in the string to replace with their values. Length must be a
     *                             multiple of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The string array from the field in config or the default value if the field doesn't exist.
     */
    public final String[] getStringArray(@NonNull final String field, final String[] defaultValue,
                                         final String... replacementVariables)
    {
        if (!fieldIsArrayOfType(field, String.class))
        {
            return defaultValue;
        }
        else
        {
            final ArrayList<String> stringArrayList = new ArrayList<>();

            for (final String line : getFileConfiguration().getStringList(field))
            {
                stringArrayList.add(MessageManager.colorize(MessageManager.replaceVariables(line,
                        replacementVariables)));
            }

            // Converts the string list to an array.
            return stringArrayList.toArray(new String[0]);
        }
    }

    /**
     * Retrieves a string array from a field in config.
     *
     * @param field                The field. Can't be null.
     * @param replacementVariables The variables in the string to replace with their values. Length must be a
     *                             multiple of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The string array from the field in the config file.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final String[] getStringArray(@NonNull final String field, final String... replacementVariables)
            throws ConfigLoadException
    {
        if (!containsField(field))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config or is not a string array.");
        }
        else
        {
            final ArrayList<String> stringArrayList = new ArrayList<>();

            for (final String line : getFileConfiguration().getStringList(field))
            {
                stringArrayList.add(MessageManager.colorize(MessageManager.replaceVariables(line,
                        replacementVariables)));
            }

            // Converts the string list to an array.
            return stringArrayList.toArray(new String[0]);
        }
    }

    /**
     * Retrieves a string from a field in config.
     *
     * @param field                The field. Can't be null.
     * @param defaultValue         The value to return if the field doesn't exist. Can be null.
     * @param replacementVariables The variables in the string to replace with their values. Length must be a
     *                             multiple of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The string from the field in config or the default value if the field doesn't exist.
     */
    public final String getString(@NonNull final String field, final String defaultValue,
                                  final String... replacementVariables)
    {
        final String stringValue = getFileConfiguration().getString(field, defaultValue);

        return MessageManager.colorize(MessageManager.replaceVariables(stringValue, replacementVariables));
    }

    /**
     * Retrieves a string from a field in config.
     *
     * @param field                The field. Can't be null.
     * @param replacementVariables The variables in the string to replace with their values. Length must be a
     *                             multiple of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The string from the field in config.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final String getString(@NonNull final String field, final String... replacementVariables)
            throws ConfigLoadException
    {
        if (!fieldIsType(field, String.class))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config or is not a string.");
        }
        else
        {
            final String stringValue = getFileConfiguration().getString(field);

            return MessageManager.colorize(MessageManager.replaceVariables(stringValue, replacementVariables));
        }
    }

    /**
     * Retrieves a string list from a field in config.
     *
     * @param field                The field. Can't be null.
     * @param defaultValue         The value to return if the field doesn't exist. Can be null.
     * @param replacementVariables The variables in the string to replace with their values. Length must be a
     *                             multiple of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The string list from the field in config or the default value if the field doesn't exist.
     */
    public final List<String> getStringList(@NonNull final String field, final List<String> defaultValue,
                                            final String... replacementVariables)
    {
        if (!fieldIsListOfType(field, String.class))
        {
            return defaultValue;
        }
        else
        {
            final ArrayList<String> stringArrayList = new ArrayList<>();

            for (final String line : getFileConfiguration().getStringList(field))
            {
                stringArrayList.add(MessageManager.colorize(MessageManager.replaceVariables(line,
                        replacementVariables)));
            }

            return stringArrayList;
        }
    }

    /**
     * Retrieves a string list from a field in config.
     *
     * @param field                The field. Can't be null.
     * @param replacementVariables The variables in the string to replace with their values. Length must be a
     *                             multiple of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The string list from the field in the config file.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final List<String> getStringList(@NonNull final String field, final String... replacementVariables)
            throws ConfigLoadException
    {
        if (!fieldIsListOfType(field, String.class))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config or is not a string list.");
        }
        else
        {
            final ArrayList<String> stringArrayList = new ArrayList<>();

            for (final String line : getFileConfiguration().getStringList(field))
            {
                stringArrayList.add(MessageManager.colorize(MessageManager.replaceVariables(line,
                        replacementVariables)));
            }

            return stringArrayList;
        }
    }

    /**
     * Retrieves a string array from a field in config.
     *
     * @param field                The field. Can't be null.
     * @param defaultValue         The value to return if the field doesn't exist. Can be null.
     * @param replacementVariables The variables in the string to replace with their values. Length must be a
     *                             multiple of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The string array from the field in config or the default value if the field doesn't exist.
     */
    public final String[] getStringArray(@NonNull final String field, final String[] defaultValue,
                                         final String... replacementVariables)
    {
        if (!fieldIsArrayOfType(field, String.class))
        {
            return defaultValue;
        }
        else
        {
            final ArrayList<String> stringArrayList = new ArrayList<>();

            for (final String line : getFileConfiguration().getStringList(field))
            {
                stringArrayList.add(MessageManager.colorize(MessageManager.replaceVariables(line,
                        replacementVariables)));
            }

            // Converts the string list to an array.
            return stringArrayList.toArray(new String[0]);
        }
    }

    /**
     * Retrieves a string array from a field in config.
     *
     * @param field                The field. Can't be null.
     * @param replacementVariables The variables in the string to replace with their values. Length must be a
     *                             multiple of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The string array from the field in the config file.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final String[] getStringArray(@NonNull final String field, final String... replacementVariables)
            throws ConfigLoadException
    {
        if (!containsField(field))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config or is not a string array.");
        }
        else
        {
            final ArrayList<String> stringArrayList = new ArrayList<>();

            for (final String line : getFileConfiguration().getStringList(field))
            {
                stringArrayList.add(MessageManager.colorize(MessageManager.replaceVariables(line,
                        replacementVariables)));
            }

            // Converts the string list to an array.
            return stringArrayList.toArray(new String[0]);
        }
    }

    /**
     * Retrieves an integer from a field in config.
     *
     * @param field        The field. Can't be null.
     * @param defaultValue The value to return if the field doesn't exist.
     *
     * @return The integer from the field in config or the default value if the field
     * doesn't exist.
     */
    public final int getInt(@Nonnull final String field, final int defaultValue)
    {
        return getFileConfiguration().getInt(field, defaultValue);
    }

    /**
     * Retrieves an integer from a field in config.
     *
     * @param field The field. Can't be null.
     *
     * @return The integer from the field in the config file.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final int getInt(@Nonnull final String field)
            throws ConfigLoadException
    {
        if (!containsField(field))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config.");
        }
        else
        {
            return getFileConfiguration().getInt(field);
        }
    }

    /**
     * Retrieves a float from a field in config.
     *
     * @param field        The field. Can't be null.
     * @param defaultValue The value to return if the field doesn't exist.
     *
     * @return The float from the field in config or the default value if the field
     * doesn't exist.
     */
    public final float getFloat(@Nonnull final String field, final float defaultValue)
    {
        return getFileConfiguration().getFloat(field, defaultValue);
    }

    /**
     * Retrieves a float from a field in config.
     *
     * @param field The field. Can't be null.
     *
     * @return The float from the field in config.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final float getFloat(@Nonnull final String field)
            throws ConfigLoadException
    {
        if (!containsField(field))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config.");
        }
        else
        {
            return getFileConfiguration().getFloat(field);
        }
    }

    /**
     * Retrieves a long from a field in config.
     *
     * @param field        The field. Can't be null.
     * @param defaultValue The value to return if the field doesn't exist.
     *
     * @return The long from the field in config.
     */
    public final long getLong(@Nonnull final String field, final long defaultValue)
    {
        return getFileConfiguration().getLong(field, defaultValue);
    }

    /**
     * Retrieves a long from a field in config.
     *
     * @param field The field. Can't be null.
     *
     * @return The long from the field in config.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final long getLong(@Nonnull final String field)
            throws ConfigLoadException
    {
        if (!containsField(field))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config.");
        }
        else
        {
            return getFileConfiguration().getLong(field);
        }
    }

    /**
     * Retrieves a double from a field in config.
     *
     * @param field        The field. Can't be null.
     * @param defaultValue The value to return if the field doesn't exist.
     *
     * @return The double from the field in config or the default value if the field
     * doesn't exist.
     */
    public final double getDouble(@Nonnull final String field, final double defaultValue)
    {
        return getFileConfiguration().getDouble(field, defaultValue);
    }

    /**
     * Retrieves a double from a field in config.
     *
     * @param field The field. Can't be null.
     *
     * @return The double from the field in config.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final double getDouble(@Nonnull final String field)
            throws ConfigLoadException
    {
        if (!containsField(field))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config.");
        }
        else
        {
            return getFileConfiguration().getDouble(field);
        }
    }

    /**
     * Retrieves a configuration section from a field in config.
     *
     * @param field The field. Can't be null.
     *
     * @return The configuration section from the field in config.
     *
     * @throws ConfigLoadException If the field doesn't exist.
     */
    public final Configuration getSection(@Nonnull final String field)
            throws ConfigLoadException
    {
        if (!containsField(field))
        {
            throw new ConfigLoadException("Field '" + field + "' doesn't exist in config.");
        }
        else
        {
            return getFileConfiguration().getSection(field);
        }
    }

}