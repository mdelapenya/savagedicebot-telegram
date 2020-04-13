package com.github.mdelapenya.savagedicebot;

import com.github.mdelapenya.savagedicebot.commands.HelpCommand;
import com.github.mdelapenya.savagedicebot.model.DiceRoll;
import com.github.mdelapenya.savagedicebot.model.RPGDice;
import com.github.mdelapenya.savagedicebot.model.TelegramUser;

import java.util.ArrayList;
import java.util.List;

public class BotInputParser {

    public static final String COMPLEX_ROLL_COMMAND = "/d ";
    public static final String HELP_COMMAND = "/help";

    public String parse(TelegramUser telegramUser, String text) {
        if(text.endsWith("@SavageDiceBot")) {
            text = text.substring(0, text.indexOf("@SavageDiceBot"));
        }

        text = text.toLowerCase();

        String usr = checkUser(telegramUser);

        if(text.startsWith(COMPLEX_ROLL_COMMAND)) {
            try {
                String res = parseFormula(text.substring(3));

                return "Resultado (" + usr + "): " + res;
            } catch (Exception e) {
                return "Error en tirada: " + text.substring(3);
            }
        } else if(text.equals(HELP_COMMAND)) {
            return new HelpCommand().execute();
        } else if(text.startsWith("/d")) {
            try {
                if(text.contains(" ")) {
                    throw new LTBException("Error en tirada: no se admiten espacios");
                }

                String res = parseFormula(text.substring(1));

                return "Resultado (" + usr + "): " + res;
            } catch (LTBException e) {
                return e.getLocalizedMessage();
            } catch (Exception e) {
                return "Error en tirada: " + text.substring(1);
            }
        }

        return "Command not valid: " + text + "\nPlease use the " + HELP_COMMAND + " command";
    }

    protected String checkUser(TelegramUser telegramUser) {
        String usr = telegramUser.getAlias();

        if(usr != null) {
            return usr;
        }

        if(telegramUser.getFirstName() != null && telegramUser.getLastName() != null) {
            return telegramUser.getFirstName() + " " + telegramUser.getLastName();
        }

        if(telegramUser.getFirstName() != null) {
            return telegramUser.getFirstName();
        }

        if(telegramUser.getLastName() != null) {
            return telegramUser.getLastName();
        }

        return "Desconocido";
    }

    protected String parseFormula(String formula) throws Exception {
        List<RPGDice> dice = new ArrayList<>();

        String[] formulas = formula.split(" ");
        for (String f : formulas) {
           RPGDice d = RPGDice.parse(f);
           if (d == null) {
               throw new LTBException("No se admite '" + f + "' como fórmula");
           }

           dice .add(d);
        }

        if (dice.size() == 10000) {
            DiceRoll diceRoll = dice.get(0).getDiceResult();

            return diceRoll.getDetail() + " = " + diceRoll.getResult();
        }

        String text = String.format("%0" + dice.size() + "d", 0).replace("0", "🎲");
        int total = 0;
        for (RPGDice die : dice) {
            DiceRoll diceRoll = die.getDiceResult();
            text = text + "\r\n" + diceRoll.getDetail();

            total += diceRoll.getResult();
        }

        return text + "\r\nTotal: " + total;
    }

}
