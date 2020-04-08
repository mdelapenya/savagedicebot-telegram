package com.github.mdelapenya.savagedicebot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import telegrambots.core.TelegramUser;

public class BotInputParserTest {

    private BotInputParser parser;
    private TelegramUser user;

    @Before
    public void setUp() {
        parser = new BotInputParser();
        user = new TelegramUser(1L, "latrucha", "que achucha", "latruchabot");
    }

    @Test
    public void testParseDiceRollComplex() {
        String result = parser.parse(user, BotInputParser.COMPLEX_ROLL_COMMAND + "2d8 + 1d4");
        Assert.assertTrue(result.contains("Resultado (latruchabot):"));
        Assert.assertTrue(result.contains("+2d8 ("));
        Assert.assertTrue(result.contains("+1d4 ("));
    }

    @Test
    public void testParseDiceIncludingWrongCharsReturnsError() {
        String result = parser.parse(user, "/d4x");
        Assert.assertEquals("No se admite 'x' en la fórmula", result);

        result = parser.parse(user, "/d4*");
        Assert.assertEquals("No se admite '*' en la fórmula", result);
    }

    @Test
    public void testParseDiceIncludingSpacesReturnsError() {
        String expected = "Error en tirada: no se admiten espacios";

        String result = parser.parse(user, "/d4 ");
        Assert.assertEquals(expected, result);

        result = parser.parse(user, "/d4 is not valid");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testParseHelpCommand() {
        String result = parser.parse(user, BotInputParser.HELP_COMMAND);

        Assert.assertTrue(result.contains("<b>LaTruchaBot - Ayuda</b>\r\n"));
        Assert.assertTrue(result.contains(" - Ejemplos:\r\n"));
        Assert.assertTrue(result.contains(" - Ejemplo: /d10s\r\n"));
        Assert.assertTrue(result.contains("Ejemplo de tirada compleja: /d 2d8 + 2d6e - 3d4s\r\n"));
    }

    @Test
    public void testParseWrongCommand() {
        String result = parser.parse(user, "this command is not valid");

        Assert.assertNull(result);
    }

}