package com.qhucy.universalenchant.util;

import lombok.NonNull;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains static utility methods for modifying and sending messages.
 *
 * @author Qhucy
 */
public final class MessageManager
{

    // List of color codes supported by Spigot.
    private final static List< Character > COLOR_CODES;

    // Loads the color codes character array from the ChatColor enumeration constants.
    static
    {
        final ArrayList< Character > chatColorCharacters = new ArrayList<>();

        for ( final ChatColor chatColor : ChatColor.values() )
        {
            chatColorCharacters.add( chatColor.getChar() );
        }

        COLOR_CODES = chatColorCharacters;
    }

    /**
     * Returns the given message with color code variables replaced by color code values.
     *
     * @param message The message. Can be null.
     *
     * @return The given message with color code variables replaced by color code values.
     */
    public static String colorize( final String message )
    {
        if ( message == null )
        {
            return null;
        }
        else
        {
            final StringBuilder stringBuilder = new StringBuilder();
            final char[]        chars         = message.toCharArray();

            for ( int i = 0; i < chars.length; i++ )
            {
                char part = chars[ i ];

                if ( part == '&' && ( i + 1 ) < chars.length && COLOR_CODES.contains( chars[ i + 1 ] ) )
                {
                    stringBuilder.append( ChatColor.COLOR_CHAR ).append( chars[ i + 1 ] );

                    i++;
                }
                else
                {
                    stringBuilder.append( part );
                }
            }

            return stringBuilder.toString();
        }
    }

    /**
     * Replaces variables inside of a message with their values.
     *
     * @param message              The message that contains variables. Can't be null.
     * @param replacementVariables The variables in the message to replace with their values. Length must be a multiple
     *                             of two with the format [variable, value, variable, value, ...]. Can be null.
     *
     * @return The stringValue with its variables being replaced with its values.
     */
    public static String replaceVariables( String message, final String[] replacementVariables )
    {
        if ( message == null )
        {
            return null;
        }
        else
        {
            if ( replacementVariables != null && replacementVariables.length > 1 && replacementVariables.length % 2 == 0 )
            {
                for ( int i = 0; i < replacementVariables.length; i += 2 )
                {
                    final String variable = replacementVariables[ i ];
                    final String value    = replacementVariables[ i + 1 ];

                    message = message.replace( variable, value );
                }
            }

            return message;
        }
    }

}