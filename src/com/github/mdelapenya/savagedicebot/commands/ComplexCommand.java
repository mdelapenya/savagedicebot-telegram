package com.github.mdelapenya.savagedicebot.commands;

public class ComplexCommand implements SavageDiceBotCommand {

    private String usr;
    private String text;

    public ComplexCommand(String usr, String text) {
        this.usr = usr;
        this.text = text;
    }

    @Override
    public String execute() {
        String res = parseFormula(this.text.substring(3));

        return "Resultado (" + this.usr + "): " + res;
    }

}
