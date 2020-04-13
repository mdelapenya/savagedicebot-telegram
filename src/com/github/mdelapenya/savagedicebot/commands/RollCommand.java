package com.github.mdelapenya.savagedicebot.commands;

public class RollCommand implements SavageDiceBotCommand {

    private String usr;
    private String text;
    private int savageFaces;

    public RollCommand(String usr, String text) {
       this(usr, text, 6);
    }

    public RollCommand(String usr, String text, int savageFaces) {
        this.usr = usr;
        this.text = text;
        this.savageFaces = savageFaces;
    }

    @Override
    public String execute() {
        String res = parseFormula(this.text.substring(6), this.savageFaces);

        return "Resultado (" + this.usr + "): " + res;
    }

}
