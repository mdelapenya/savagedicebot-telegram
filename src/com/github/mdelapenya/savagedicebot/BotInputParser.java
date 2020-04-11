package com.github.mdelapenya.savagedicebot;

import telegrambots.core.TelegramUser;

public class BotInputParser {

    public static final String COMPLEX_ROLL_COMMAND = "/d ";
    public static final String HELP_COMMAND = "/dhelp";
    private static final String VALID_CHARS = "0123456789des+-";

    public String parse(TelegramUser telegramUser, String text) {
        if(text.endsWith("@LaTruchaBot")) {
            text = text.substring(0, text.indexOf("@LaTruchaBot"));
        }

        text = text.toLowerCase();

        String usr = checkUser(telegramUser);

        if(text.startsWith(COMPLEX_ROLL_COMMAND)) {
            try {
                String res = parseFormula(text.substring(3));

                return "Resultado (" + usr + "): " + res;
            } catch (Exception e) {
                return "Error en tirada: " + text.substring(3);
            }
        } else if(text.equals(HELP_COMMAND)) {
            StringBuilder sb = new StringBuilder(24);

            sb.append("<b>LaTruchaBot - Ayuda</b>\r\n");
            sb.append(" - Formato de dado: +/-NdC[e o sS]\r\n");
            sb.append("   · N: número de dados\r\n");
            sb.append("   · C: número de cara de los dados\r\n");
            sb.append("   · e: para indicar que el dado explora\r\n");
            sb.append("   · s: para indicar que es un dado salvaje\r\n");
            sb.append("   · S: en caso de dado salvaje, número de caras del dado salvaje\r\n");
            sb.append(" - Ejemplos:\r\n");
            sb.append("   · /d +2d8: 2 dados de 8\r\n");
            sb.append("   · /d +1d6e: 1 dado de 6 que puede explotar\r\n");
            sb.append("   · /d +1d8s6: 1 dado de 8 y 1 dado de 6 salvaje (ambos explotan)\r\n");
            sb.append("Se pueden sumar o restar varios dados en una misma tirada:\r\n");
            sb.append(" - Ejemplo: +2d8 - 2d4e\r\n");
            sb.append("Si se va realizar una tirada con dado salvaje d6 no es necesario añadir el 6.\r\n");
            sb.append(" - Ejemplo: +1d8s  =  +1d8s6\r\n");
            sb.append("Si el primer dado de la tirada es para sumar no es neceario añadir el +.\r\n");
            sb.append(" - Ejemplo: +1d8s  =  1d8s\r\n");
            sb.append("Si solo se va a lanzar un dado no es necesario añadir el 1.\r\n");
            sb.append(" - Ejemplo: 1d8s  =  d8s\r\n");
            sb.append("Se puede realizar una tirada simple de forma directa.\r\n");
            sb.append(" - Ejemplo: /d10s\r\n");
            sb.append("\r\n");
            sb.append("Ejemplo de tirada compleja: /d 2d8 + 2d6e - 3d4s\r\n");
            sb.append("Ejemplo de tirada simple: /d10s8");

            return sb.toString();
        } else if(text.startsWith("/d")) {
            try {
                if(text.contains(" ")) {
                    throw new LTBException("Error en tirada: no se admiten espacios");
                }

                String res = parseFormula(text.substring(1));

                return "Resultado (" + usr + "): " + res;
            } catch (LTBException e) {
                return e.getLocalizedMessage();
            } catch (Exception e) {
                return "Error en tirada: " + text.substring(1);
            }
        }

        return null;
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

    protected String parseFormula(String formula) throws Exception {
        formula = formula.toLowerCase().replace(" ", "");

        for(char ch : formula.toCharArray()) {
            if(this.VALID_CHARS.indexOf(ch) < 0) {
                throw new LTBException("No se admite '" + new String(new char[]{ch}) + "' en la fórmula");
            }
        }

        if(!formula.startsWith("+") && !formula.startsWith("-")) {
            formula = "+" + formula;
        }

        formula = formula.replace("+", ";+");
        formula = formula.replace("-", ";-");

        formula = formula.substring(1);

        String[] dice = formula.split(";");

        String text = "";

        if(dice.length == 1) {
            DiceRoll diceRoll = new Dice(dice[0]).getDiceResult();
            text = text + " " + diceRoll.getDetail();
            return text + " = " + diceRoll.getResult();
        } else {
            int total = 0;
            for(String dado : dice) {
                DiceRoll diceRoll = new Dice(dado).getDiceResult();
                text = text + "\r\n" + diceRoll.getDetail();

                total += diceRoll.getResult();
            }

            return text + "\r\nTotal: " + total;
        }
    }

}
