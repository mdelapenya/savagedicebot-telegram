package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.LTBException;
import com.github.mdelapenya.savagedicebot.model.TelegramUser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleCommandTest {

    private TelegramUser user;

    @Before
    public void setUp() {
        user = new TelegramUser(1L, "Manu", "de la Peña", "mdelapenya");
    }

    @Test(expected = LTBException.class)
    public void testParseDiceIncludingWrongCharsReturnsError() {
        new SimpleCommand(user.getAlias(), "/d4x").execute();
        Assert.fail("No se admite 'd4x' como fórmula");
    }

    @Test(expected = LTBException.class)
    public void testParseDiceIncludingSpacesReturnsError() {
        new SimpleCommand(user.getAlias(), "/d4 ").execute();
        Assert.fail("Error en tirada: no se admiten espacios");
    }

}
