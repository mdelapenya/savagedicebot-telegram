package com.github.mdelapenya.savagedicebot;

import com.github.mdelapenya.savagedicebot.commands.HelpCommand;
import com.github.mdelapenya.savagedicebot.commands.RollCommand;
import com.github.mdelapenya.savagedicebot.commands.WrongCommand;
import com.github.mdelapenya.savagedicebot.model.TelegramUser;

public class BotInputParser {

    public static final String DEFAULT_ROLL_COMMAND = "/roll ";
    public static final String HELP_COMMAND = "/help";

    public String parse(TelegramUser telegramUser, String text) {
        if(text.endsWith("@SavageDiceBot")) {
            text = text.substring(0, text.indexOf("@SavageDiceBot"));
        }

        text = text.toLowerCase();

        String usr = checkUser(telegramUser);

        if(text.startsWith(DEFAULT_ROLL_COMMAND)) {
            try {
                return new RollCommand(usr, text).execute();
            } catch (Exception e) {
                return "Error en tirada: " + text.substring(6);
            }
        } else if(text.equals(HELP_COMMAND)) {
            return new HelpCommand().execute();
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
