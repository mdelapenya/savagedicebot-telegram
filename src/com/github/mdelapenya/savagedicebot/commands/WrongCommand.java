package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.BotInputParser;

public class WrongCommand implements SavageDiceBotCommand {

    private String text;

    public WrongCommand(String text) {
        this.text = text;
    }

    @Override
    public String execute() {
        return "Command not valid: " + this.text + "\nPlease use the " + BotInputParser.HELP_COMMAND + " command";
    }
}
