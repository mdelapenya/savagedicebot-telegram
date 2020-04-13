package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.BotInputParser;

public class WrongCommand implements SavageDiceBotCommand {

    private String command;

    public WrongCommand(String command) {
        this.command = command;
    }

    @Override
    public String execute() {
        return "Command not valid: " + this.command + "\nPlease use the " + BotInputParser.HELP_COMMAND + " command";
    }
}
