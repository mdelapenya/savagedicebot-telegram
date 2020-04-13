package com.github.mdelapenya.savagedicebot;

import com.github.mdelapenya.savagedicebot.commands.ComplexCommand;
import com.github.mdelapenya.savagedicebot.commands.HelpCommand;
import com.github.mdelapenya.savagedicebot.commands.SimpleCommand;
import com.github.mdelapenya.savagedicebot.commands.WrongCommand;
import com.github.mdelapenya.savagedicebot.model.TelegramUser;

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
                return new ComplexCommand(usr, text).execute();
            } catch (Exception e) {
                return "Error en tirada: " + text.substring(3);
            }
        } else if(text.equals(HELP_COMMAND)) {
            return new HelpCommand().execute();
        } else if(text.startsWith("/d")) {
            try {
                return new SimpleCommand(usr, text).execute();
            } catch (LTBException e) {
                return e.getLocalizedMessage();
            } catch (Exception e) {
                return "Error en tirada: " + text.substring(1);
            }
        }

        return new WrongCommand(text).execute();
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

}
