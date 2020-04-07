package org.latruchabot;

import telegrambots.core.TelegramBot;
import telegrambots.core.TelegramBotListener;
import telegrambots.core.TelegramBotMessage;

public class LaTruchaBot extends TelegramBot implements TelegramBotListener
{
    private String caracteresValidos = "0123456789des+-";
    
    public LaTruchaBot()
    {
        super("<YOUR_BOT_NAME>", "<YOUR_BOT_TOKEN>", false, false);
        this.setTelegramBotListener(this);
    }
    
    @Override
    public void reciveMessage(TelegramBotMessage message)
    {
        //System.out.println(message.getChatID() + " - " + message.getMessageText());
        
        String text = message.getMessageText();
        
        if(text.endsWith("@LaTruchaBot"))
        {
            text = text.substring(0, text.indexOf("@LaTruchaBot"));
        }
        
        text = text.toLowerCase();
        
        String usr = message.getUser().getAlias();
            
        if(usr == null)
        {
            if(message.getUser().getFirstName() != null && message.getUser().getLastName() != null)
            {
                usr = message.getUser().getFirstName() + " " + message.getUser().getLastName();
            }
            else if(message.getUser().getFirstName() != null)
            {
                usr = message.getUser().getFirstName();
            }
            else if(message.getUser().getLastName() != null)
            {
                usr = message.getUser().getLastName();
            }
            else
            {
                usr = "Desconocido";
            }
        }

        long chatID = message.getChatID();

        if(text.startsWith("/d "))
        {
            try
            {
                String res = this.parsearFormula(text.substring(3));
                
                this.sendText(chatID, "Resultado (" + usr + "): " + res);
            }
            catch (Exception e)
            {
                this.sendText(chatID, "Error en tirada: " + text.substring(3));
            }
        }
        else if(text.equals("/dhelp"))
        {
            this.sendText(chatID, "<b>LaTruchaBot - Ayuda</b>\r\n"
                    + " - Formato de dado: +/-NdC[e o sS]\r\n"
                    + "   � N: n�mero de dados\r\n"
                    + "   � C: n�mero de cara de los dados\r\n"
                    + "   � e: para indicar que el dado explora\r\n"
                    + "   � s: para indicar que es un dado salvaje\r\n"
                    + "   � S: en caso de dado salvaje, n�mero de caras del dado salvaje\r\n"
                    + " - Ejemplos:\r\n"
                    + "   � /d +2d8: 2 dados de 8\r\n"
                    + "   � /d +1d6e: 1 dado de 6 que puede explotar\r\n"
                    + "   � /d +1d8s6: 1 dado de 8 y 1 dado de 6 salvaje (ambos explotan)\r\n"
                    + "Se pueden sumar o restar varios dados en una misma tirada:\r\n"
                    + " - Ejemplo: +2d8 - 2d4e\r\n"
                    + "Si se va realizar una tirada con dado salvaje d6 no es necesario a�adir el 6.\r\n"
                    + " - Ejemplo: +1d8s  =  +1d8s6\r\n"
                    + "Si el primer dado de la tirada es para sumar no es neceario a�adir el +.\r\n"
                    + " - Ejemplo: +1d8s  =  1d8s\r\n"
                    + "Si solo se va a lanzar un dado no es necesario a�adir el 1.\r\n"
                    + " - Ejemplo: 1d8s  =  d8s\r\n"
                    + "Se puede realizar una tirada simple de forma directa.\r\n"
                    + " - Ejemplo: /d10s\r\n"
                    + "\r\n"
                    + "Ejemplo de tirada compleja: /d 2d8 + 2d6e - 3d4s\r\n"
                    + "Ejemplo de tirada simple: /d10s8");
        }
        else if(text.startsWith("/d"))
        {
            try
            {
                if(text.contains(" ")) throw new LTBException("Error en tirada");
                
                String res = this.parsearFormula(text.substring(1));
                
                this.sendText(chatID, "Resultado (" + usr + "): " + res);
            }
            catch (Exception e)
            {
                this.sendText(chatID, "Error en tirada: " + text.substring(1));
            }
        }
    }
    
    private String parsearFormula(String formula) throws Exception
    {
        formula = formula.toLowerCase().replace(" ", "");
        
        for(char ch : formula.toCharArray())
        {
            if(this.caracteresValidos.indexOf(ch) < 0)
            {
                throw new LTBException("No se admite '" + new String(new char[]{ch}) + "' en la f�rmula");
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
