package org.latruchabot;

import telegrambots.core.TelegramUser;

public class BotInputParser {

    private String caracteresValidos = "0123456789des+-";

    public String parse(TelegramUser telegramUser, String text) {
        if(text.endsWith("@LaTruchaBot"))
        {
            text = text.substring(0, text.indexOf("@LaTruchaBot"));
        }

        text = text.toLowerCase();

        String usr = telegramUser.getAlias();

        if(usr == null)
        {
            if(telegramUser.getFirstName() != null && telegramUser.getLastName() != null)
            {
                usr = telegramUser.getFirstName() + " " + telegramUser.getLastName();
            }
            else if(telegramUser.getFirstName() != null)
            {
                usr = telegramUser.getFirstName();
            }
            else if(telegramUser.getLastName() != null)
            {
                usr = telegramUser.getLastName();
            }
            else
            {
                usr = "Desconocido";
            }
        }


        String resultText = null;

        if(text.startsWith("/d "))
        {
            try
            {
                String res = this.parsearFormula(text.substring(3));

                resultText = "Resultado (" + usr + "): " + res;
            }
            catch (Exception e)
            {
                resultText = "Error en tirada: " + text.substring(3);
            }
        }
        else if(text.equals("/dhelp"))
        {
            resultText = "<b>LaTruchaBot - Ayuda</b>\r\n"
                + " - Formato de dado: +/-NdC[e o sS]\r\n"
                + "   · N: número de dados\r\n"
                + "   · C: número de cara de los dados\r\n"
                + "   · e: para indicar que el dado explora\r\n"
                + "   · s: para indicar que es un dado salvaje\r\n"
                + "   · S: en caso de dado salvaje, número de caras del dado salvaje\r\n"
                + " - Ejemplos:\r\n"
                + "   · /d +2d8: 2 dados de 8\r\n"
                + "   · /d +1d6e: 1 dado de 6 que puede explotar\r\n"
                + "   · /d +1d8s6: 1 dado de 8 y 1 dado de 6 salvaje (ambos explotan)\r\n"
                + "Se pueden sumar o restar varios dados en una misma tirada:\r\n"
                + " - Ejemplo: +2d8 - 2d4e\r\n"
                + "Si se va realizar una tirada con dado salvaje d6 no es necesario añadir el 6.\r\n"
                + " - Ejemplo: +1d8s  =  +1d8s6\r\n"
                + "Si el primer dado de la tirada es para sumar no es neceario añadir el +.\r\n"
                + " - Ejemplo: +1d8s  =  1d8s\r\n"
                + "Si solo se va a lanzar un dado no es necesario añadir el 1.\r\n"
                + " - Ejemplo: 1d8s  =  d8s\r\n"
                + "Se puede realizar una tirada simple de forma directa.\r\n"
                + " - Ejemplo: /d10s\r\n"
                + "\r\n"
                + "Ejemplo de tirada compleja: /d 2d8 + 2d6e - 3d4s\r\n"
                + "Ejemplo de tirada simple: /d10s8";
        }
        else if(text.startsWith("/d"))
        {
            try
            {
                if(text.contains(" ")) throw new LTBException("Error en tirada");

                String res = this.parsearFormula(text.substring(1));

                resultText = "Resultado (" + usr + "): " + res;
            }
            catch (Exception e)
            {
                resultText = "Error en tirada: " + text.substring(1);
            }
        }

        return resultText;
    }

    private String parsearFormula(String formula) throws Exception
    {
        formula = formula.toLowerCase().replace(" ", "");

        for(char ch : formula.toCharArray())
        {
            if(this.caracteresValidos.indexOf(ch) < 0)
            {
                throw new LTBException("No se admite '" + new String(new char[]{ch}) + "' en la fórmula");
            }
        }

        if(!formula.startsWith("+") && !formula.startsWith("-"))
        {
            formula = "+" + formula;
        }

        formula = formula.replace("+", ";+");
        formula = formula.replace("-", ";-");

        formula = formula.substring(1);

        String[] dados = formula.split(";");

        String texto = "";

        if(dados.length == 1)
        {
            ResultadoTirada resultadoTirada = new Dado(dados[0]).getResultadoTirada();
            texto = texto + " " + resultadoTirada.getDetalle();
            return texto + " = " + resultadoTirada.getResultado();
        }
        else
        {
            int total = 0;
            for(String dado : dados)
            {
                ResultadoTirada resultadoTirada = new Dado(dado).getResultadoTirada();
                texto = texto + "\r\n" + resultadoTirada.getDetalle();

                total += resultadoTirada.getResultado();
            }

            return texto + "\r\nTotal: " + total;
        }
    }

}