package com.github.mdelapenya.savagedicebot.commands;

import org.junit.Assert;
import org.junit.Test;

public class HelpCommandTest {

    @Test
    public void testParseHelpCommand() {
        String result = new HelpCommand().execute();

        Assert.assertTrue(result.contains("🎲<b>SavageDiceBot - Help</b>🎲\r\n"));
        Assert.assertTrue(result.contains("\nExamples:\r\n"));
        Assert.assertTrue(result.contains("\t· /roll +1d6e: one exploding d6\r\n"));
        Assert.assertTrue(result.contains("\t· /roll 2d8 2d6e -3d4\r\n"));
    }

}
