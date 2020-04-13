package com.github.mdelapenya.savagedicebot.commands;

public class HelpCommand implements SavageDiceBotCommand {
    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder(24);

        sb.append("<b>SavageDiceBot - Ayuda</b>\r\n");
        sb.append(" - Formato de dado: [+|-][?]NdC[e]\r\n");
        sb.append("   · N: número de dados\r\n");
        sb.append("   · C: número de cara de los dados\r\n");
        sb.append("   · e: para indicar que el dado explora\r\n");
        sb.append(" - Ejemplos:\r\n");
        sb.append("   · /d +2d8: 2 dados de 8\r\n");
        sb.append("   · /d +1d6e: 1 dado de 6 que puede explotar\r\n");
        sb.append("   · /d +!1d6e: 1 dado de 6 que puede explotar SIN dado salvage\r\n");
        sb.append("Se pueden sumar o restar varios dados en una misma tirada:\r\n");
        sb.append(" - Ejemplo: +2d8 -2d4e\r\n");
        sb.append("Si se va realizar una tirada con dado salvaje d6 no es necesario añadir el 6.\r\n");
        sb.append(" - Ejemplo: +1d8  =  +1d8\r\n");
        sb.append("Si el primer dado de la tirada es para sumar no es necesario añadir el +.\r\n");
        sb.append(" - Ejemplo: +1d8s  =  1d8s\r\n");
        sb.append("Si solo se va a lanzar un dado no es necesario añadir el 1.\r\n");
        sb.append(" - Ejemplo: 1d8s  =  d8s\r\n");
        sb.append("Se puede realizar una tirada simple de forma directa.\r\n");
        sb.append(" - Ejemplo: /d10\r\n");
        sb.append("\r\n");
        sb.append("Ejemplo de tirada compleja: /d 2d8 +2d6e -3d4\r\n");
        sb.append("Ejemplo de tirada simple: /d10");

        return sb.toString();
    }

}
