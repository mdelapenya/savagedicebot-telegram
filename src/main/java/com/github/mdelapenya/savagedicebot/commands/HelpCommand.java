package com.github.mdelapenya.savagedicebot.commands;

public class HelpCommand implements SavageDiceBotCommand {

    private String header;

    public HelpCommand() {
        this.header = "";
    }

    public HelpCommand(String header) {
        this.header = header + "\r\n\n";
    }

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder(25);

        sb.append(header);
        sb.append("🎲<b>SavageDiceBot - Help</b>🎲\r\n");
        sb.append("\nFormat: [+|-][!]NdF[e]\r\n");
        sb.append("\t· !: this 🎲 does not adds a savage 🎲 (optional, default true)\r\n");
        sb.append("\t· N: number of rolls\r\n");
        sb.append("\t· F: number of faces\r\n");
        sb.append("\t· e: this 🎲 explodes (optional, default false)\r\n");
        sb.append("\nExamples:\r\n");
        sb.append("\t· /roll +2d8: two d8\r\n");
        sb.append("\t· /roll +1d6e: one exploding d6\r\n");
        sb.append("\t· /roll !d6e: one exploding d6 without savage dice\r\n");
        sb.append("\nIt's possible to add several 🎲 in a row:\r\n");
        sb.append("Example: /roll 2d8 -2d4e\r\n");
        sb.append("\nTo roll a 🎲 with a savage dice different than d6:\r\n");
        sb.append("Example: /roll4 +1d8: one d8 using d4 as the savage 🎲\r\n");
        sb.append("Example: /roll8 +1d8: one d8 using d8 as the savage 🎲\r\n");
        sb.append("Example: /roll10 +1d8: one d8 using d10 as the savage 🎲\r\n");
        sb.append("\nIf a 🎲 is positive, it's not needed to add the sign (+-).\r\n");
        sb.append("Example:\r\n");
        sb.append("\t· /roll 1d8 1d4: one d8 plus one d4\r\n");
        sb.append("\nIt's not needed to add the 1 if there is only one 🎲 in the roll.\r\n");
        sb.append("Example: 1d8s  =>  d8s\r\n");
        sb.append("\nComplex Examples:\r\n");
        sb.append("\t· /roll 2d8 2d6e -3d4\r\n");
        sb.append("\t· /roll8 2d8 2d6e -3d4\r\n");
        sb.append("\t· /roll10 2d8 2d6e -3d4");

        return sb.toString();
    }

}
