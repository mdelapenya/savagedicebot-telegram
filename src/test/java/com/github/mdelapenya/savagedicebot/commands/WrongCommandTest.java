package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.BotInputParser;

import org.junit.Assert;
import org.junit.Test;

public class WrongCommandTest {

    @Test
    public void testParseWrongCommand() {
        String result = new WrongCommand("this command is not valid").execute();

        Assert.assertEquals("Command not valid: this command is not valid\nPlease use the " + BotInputParser.HELP_COMMAND + " command", result);
    }

}
