package org.latruchabot;

import telegrambots.core.TelegramUser;

public class BotInputParser {

    public static final String COMPLEX_ROLL_COMMAND = "/d ";
    public static final String HELP_COMMAND = "/dhelp";

    private String caracteresValidos = "0123456789des+-";

    public String parse(TelegramUser telegramUser, String text) {
        if(text.endsWith("@LaTruchaBot")) {
            text = text.substring(0, text.indexOf("@LaTruchaBot"));
        }

        text = text.toLowerCase();

        String usr = telegramUser.getAlias();

        if(usr == null) {
            if(telegramUser.getFirstName() != null && telegramUser.getLastName() != null) {
                usr = telegramUser.getFirstName() + " " + telegramUser.getLastName();
            }
            else if(telegramUser.getFirstName() != null) {
                usr = telegramUser.getFirstName();
            }
            else if(telegramUser.getLastName() != null) {
                usr = telegramUser.getLastName();
            }
            else {
                usr = "Desconocido";
            }
        }

        if(text.startsWith(COMPLEX_ROLL_COMMAND)) {
            try {
                String res = this.parsearFormula(text.substring(3));

                return "Resultado (" + usr + "): " + res;
            }
            catch (Exception e) {
                return "Error en tirada: " + text.substring(3);
            }
        }
        else if(text.equals(HELP_COMMAND)) {
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
        }
        else if(text.startsWith("/d")) {
            try {
                if(text.contains(" ")) {
                    throw new LTBException("Error en tirada: no se admiten espacios");
                }

                String res = this.parsearFormula(text.substring(1));

                return "Resultado (" + usr + "): " + res;
            }
            catch (LTBException e) {
                return e.getLocalizedMessage();
            }
            catch (Exception e) {
                return "Error en tirada: " + text.substring(1);
            }
        }

        return null;
    }

    private String parsearFormula(String formula) throws Exception {
        formula = formula.toLowerCase().replace(" ", "");

        for(char ch : formula.toCharArray()) {
            if(this.caracteresValidos.indexOf(ch) < 0) {
                throw new LTBException("No se admite '" + new String(new char[]{ch}) + "' en la fórmula");
            }
        }

        if(!formula.startsWith("+") && !formula.startsWith("-")) {
            formula = "+" + formula;
        }

        formula = formula.replace("+", ";+");
        formula = formula.replace("-", ";-");

        formula = formula.substring(1);

        String[] dados = formula.split(";");

        String texto = "";

        if(dados.length == 1) {
            ResultadoTirada resultadoTirada = new Dado(dados[0]).getResultadoTirada();
            texto = texto + " " + resultadoTirada.getDetalle();
            return texto + " = " + resultadoTirada.getResultado();
        }
        else {
            int total = 0;
            for(String dado : dados) {
                ResultadoTirada resultadoTirada = new Dado(dado).getResultadoTirada();
                texto = texto + "\r\n" + resultadoTirada.getDetalle();

                total += resultadoTirada.getResultado();
            }

            return texto + "\r\nTotal: " + total;
        }
    }

}