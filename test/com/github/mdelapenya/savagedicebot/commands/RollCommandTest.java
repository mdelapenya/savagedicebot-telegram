package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.BotInputParser;
import com.github.mdelapenya.savagedicebot.model.TelegramUser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RollCommandTest {

    private TelegramUser user;

    @Before
    public void setUp() {
        user = new TelegramUser(1L, "Manu", "de la Peña", "mdelapenya");
    }

    @Test
    public void testParseDiceRoll() {
        String result = new RollCommand(user.getAlias(), BotInputParser.DEFAULT_ROLL_COMMAND + " 2d8 1d4").execute();

        Assert.assertTrue(result.contains("Resultado (mdelapenya):"));
        Assert.assertTrue(result.contains("+2d8 ("));
        Assert.assertTrue(result.contains("+1d4 ("));
    }

    @Test
    public void testParseDiceRollIncludingSigns() {
        String result = new RollCommand(user.getAlias(), BotInputParser.DEFAULT_ROLL_COMMAND + " -2d8 -1d4").execute();

        Assert.assertTrue(result.contains("Resultado (mdelapenya):"));
        Assert.assertTrue(result.contains("-2d8 ("));
        Assert.assertTrue(result.contains("-1d4 ("));
    }

}
