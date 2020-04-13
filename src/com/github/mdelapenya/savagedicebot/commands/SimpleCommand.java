package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.LTBException;

public class SimpleCommand implements SavageDiceBotCommand {

    private String usr;
    private String text;

    public SimpleCommand(String usr, String text) {
        this.usr = usr;
        this.text = text;
    }

    @Override
    public String execute() {
        if(text.contains(" ")) {
            throw new LTBException("Error en tirada: no se admiten espacios");
        }

        String res = parseFormula(this.text.substring(1));

        return "Resultado (" + this.usr + "): " + res;
    }

}
