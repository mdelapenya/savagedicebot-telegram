package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.BotInputParser;
import com.github.mdelapenya.savagedicebot.model.RPGDice;
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
    public void testGetCommand() {
        RollCommand command = RollCommand.getCommand(user.getAlias(), "/roll 2d8 1d4");
        Assert.assertNotNull(command);

        Assert.assertEquals(RPGDice.DEFAULT_SAVAGE_FACES, command.savageFaces);
    }

    @Test
    public void testGetCommandWithDifferentSavageDice() {
        RollCommand command = RollCommand.getCommand(user.getAlias(), "/roll8 2d8 1d4");
        Assert.assertNotNull(command);

        Assert.assertEquals(8, command.savageFaces);

        command = RollCommand.getCommand(user.getAlias(), "/roll24 2d8 1d4");
        Assert.assertNotNull(command);

        Assert.assertEquals(24, command.savageFaces);
    }

    @Test
    public void testGetCommandWithWrongSavageDice() {
        RollCommand command = RollCommand.getCommand(user.getAlias(), "/rolla 2d8 1d4");
        Assert.assertNull(command);

        command = RollCommand.getCommand(user.getAlias(), "/roll2a4 2d8 1d4");
        Assert.assertNull(command);

        command = RollCommand.getCommand(user.getAlias(), "/roll21a 2d8 1d4");
        Assert.assertNull(command);
    }

    @Test
    public void testParseDiceRoll() {
        RollCommand command = RollCommand.getCommand(user.getAlias(), "/roll 2d8 1d4");
        Assert.assertNotNull(command);

        String result = command.execute();

        Assert.assertTrue(result.contains("Resultado (mdelapenya):"));
        Assert.assertTrue(result.contains("+2d8 ("));
        Assert.assertTrue(result.contains("+1d4 ("));
    }

    @Test
    public void testParseDiceRollIncludingSigns() {
        RollCommand command = RollCommand.getCommand(user.getAlias(), BotInputParser.DEFAULT_ROLL_COMMAND + " -2d8 -1d4");
        Assert.assertNotNull(command);

        String result = command.execute();

        Assert.assertTrue(result.contains("Resultado (mdelapenya):"));
        Assert.assertTrue(result.contains("-2d8 ("));
        Assert.assertTrue(result.contains("-1d4 ("));
    }

}
