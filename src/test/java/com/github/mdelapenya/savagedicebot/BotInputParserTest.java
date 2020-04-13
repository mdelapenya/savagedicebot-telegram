package com.github.mdelapenya.savagedicebot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.mdelapenya.savagedicebot.model.TelegramUser;

public class BotInputParserTest {

    private BotInputParser parser;
    private TelegramUser user;

    @Before
    public void setUp() {
        parser = new BotInputParser();
        user = new TelegramUser(1L, "Manu", "de la Peña", "mdelapenya");
    }

    @Test
    public void testCheckTelegramUserWithoutAlias() {
        user = new TelegramUser(1L, "Manu", "de la Peña", null);
        String result = parser.checkUser(user);

        Assert.assertEquals("Manu de la Peña", result);
    }

    @Test
    public void testCheckTelegramUserWithFirstNameOnly() {
        user = new TelegramUser(1L, "Manu", null, null);
        String result = parser.checkUser(user);

        Assert.assertEquals("Manu", result);
    }

    @Test
    public void testCheckTelegramUserWithLastNameOnly() {
        user = new TelegramUser(1L, null, "de la Peña", null);
        String result = parser.checkUser(user);

        Assert.assertEquals("de la Peña", result);
    }

    @Test
    public void testCheckTelegramUserWithoutValues() {
        user = new TelegramUser(1L, null, null, null);
        String result = parser.checkUser(user);

        Assert.assertEquals("Unknown", result);
    }

}