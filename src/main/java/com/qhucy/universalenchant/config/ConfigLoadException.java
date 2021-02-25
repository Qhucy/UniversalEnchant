package com.qhucy.universalenchant.config;

import lombok.NonNull;

/**
 * Exception thrown when there is an error loading a configuration (config) file or a field in a config file.
 *
 * @see Config
 *
 * @author Qhucy
 * @since 08 Feb, 2021
 */
public final class ConfigLoadException extends Exception
{

    /**
     * Sends a message and stacktrace with the reason and cause
     * of the exception.
     *
     * @param message The reason why the exception was thrown. Can't be null.
     */
    public ConfigLoadException(@NonNull final String message)
    {
        super(message);
    }

}