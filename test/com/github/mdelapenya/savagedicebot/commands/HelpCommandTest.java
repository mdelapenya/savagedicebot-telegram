package com.github.mdelapenya.savagedicebot.commands;

import org.junit.Assert;
import org.junit.Test;

public class HelpCommandTest {

    @Test
    public void testParseHelpCommand() {
        String result = new HelpCommand().execute();

        Assert.assertTrue(result.contains("<b>SavageDiceBot - Ayuda</b>\r\n"));
        Assert.assertTrue(result.contains(" - Ejemplos:\r\n"));
        Assert.assertTrue(result.contains(" - Ejemplo: /d10\r\n"));
        Assert.assertTrue(result.contains("Ejemplo de tirada compleja: /d 2d8 +2d6e -3d4\r\n"));
    }

}
