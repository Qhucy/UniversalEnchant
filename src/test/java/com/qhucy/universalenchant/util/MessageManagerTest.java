package com.qhucy.universalenchant.util;

import org.bukkit.ChatColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.qhucy.universalenchant.util.MessageManager.*;

@DisplayName("MessageManager Testing")
class MessageManagerTest
{

    final static char colorChar = ChatColor.COLOR_CHAR;

    @Test
    @DisplayName("Colorizing Empty Message")
    void colorizeEmptyMessage()
    {
        assertEquals(colorize(""), "");
    }

    @Test
    @DisplayName("Colorizing Without Colors")
    void colorizeWithNoColors()
    {
        final String base = "This is a message.";

        assertEquals(colorize(base), base);
    }

    @Test
    @DisplayName("Colorizing With One Color")
    void colorizeWithOneColor()
    {
        assertEquals(colorize("&cMessage"), colorChar + "cMessage");
    }

    @Test
    @DisplayName("Colorizing With Multiple Colors")
    void colorizeWithMultipleColors()
    {
        assertEquals(colorize("&aHello &bWorld&e!"), colorChar + "aHello " + colorChar + "bWorld" + colorChar + "e!");
    }

    @Test
    @DisplayName("Replacing Message With Null Variables")
    void replaceVariablesWithNullVariables()
    {
        final String base = "This is a message.";

        assertEquals(replaceVariables(base, null), base);
    }

    @Test
    @DisplayName("Replacing Message With No Variables")
    void replaceVariablesWithNoVariables()
    {
        final String base = "This is a message.";

        assertEquals(replaceVariables(base, new String[]{}), base);
    }

    @Test
    @DisplayName("Replacing Message With Invalid Variables Length")
    void replaceVariablesWithInvalidVariablesLength()
    {
        final String base = "This is a message.";

        assertEquals(replaceVariables(base, new String[]{"length must be", "divisible", "by 2"}), base);
    }

    @Test
    @DisplayName("Replacing Message With One Variable")
    void replaceVariablesWithOneVariable()
    {
        assertEquals(replaceVariables("I am from {state}.", new String[]{"{state}", "California"}), "I am from " +
                "California.");
    }

    @Test
    @DisplayName("Replacing Message With One Variable And Multiple Occurrences")
    void replaceVariablesWithOneVariableAndMultipleOccurrences()
    {
        assertEquals(replaceVariables("I am from {state} and my mother is from {state}.", new String[]{"{state}",
                "California"}), "I am from California and my mother is from California.");
    }

    @Test
    @DisplayName("Replacing Message With Variables And No Occurrence")
    void replaceVariablesWithVariablesAndNoOccurrence()
    {
        assertEquals(replaceVariables("Hello world!", new String[]{"{money}", "0"}), "Hello world!");
    }

    @Test
    @DisplayName("Replacing Message With Multiple Variables and Occurrences")
    void replaceVariablesWithMultipleVariablesAndOccurrences()
    {
        assertEquals(replaceVariables("!{car}:{element}#{element}*{car}({color}", new String[]{"{car}", "Tesla",
                "{element}", "Fire", "{color}", "purple"}), "!Tesla:Fire#Fire*Tesla(purple");
    }

}