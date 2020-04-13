package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.Utilities;
import com.github.mdelapenya.savagedicebot.model.RPGDice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RollCommand implements SavageDiceBotCommand {

    private static final Pattern COMMAND_PATTERN = Pattern.compile("/roll(?>(?<DICE>\\d*))?\\s(?>(?<COMMAND>.*))?");

    private String usr;
    private String command;
    protected int savageFaces;

    public RollCommand(String usr, String command, int savageFaces) {
        this.usr = usr;
        this.command = command;
        this.savageFaces = savageFaces;
    }

    public static RollCommand getCommand(String user, String text) {
        Matcher matcher = COMMAND_PATTERN.matcher(text);
        if (matcher.matches()) {
            int faces = Utilities.getInt(matcher, "DICE", RPGDice.DEFAULT_SAVAGE_FACES);
            String command = Utilities.getString(matcher, "COMMAND", "");

            return new RollCommand(user, command, faces);
        }

        return null;
    }

    @Override
    public String execute() {
        try {
            String res = parseFormula(this.command, this.savageFaces);

            return "Resultado (" + this.usr + "): " + res;
        } catch (Exception e) {
            return "Error en tirada: " + command;
        }
    }

}
