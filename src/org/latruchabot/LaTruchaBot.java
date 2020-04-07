package org.latruchabot;

import telegrambots.core.TelegramBot;
import telegrambots.core.TelegramBotListener;
import telegrambots.core.TelegramBotMessage;
import telegrambots.core.TelegramUser;

public class LaTruchaBot extends TelegramBot implements TelegramBotListener
{

    public LaTruchaBot()
    {
        super("<YOUR_BOT_NAME>", "<YOUR_BOT_TOKEN>", false, false);
        this.setTelegramBotListener(this);
    }
    
    @Override
    public void reciveMessage(TelegramBotMessage message)
    {
        //System.out.println(message.getChatID() + " - " + message.getMessageText());

        long chatID = message.getChatID();
        TelegramUser telegramUser = message.getUser();
        String resultText = new BotInputParser().parse(telegramUser, message.getMessageText());

        if (resultText != null) {
            this.sendText(chatID, resultText);
        }
    }

}
