package com.github.mdelapenya.savagedicebot.model;

import com.github.mdelapenya.savagedicebot.BotInputParser;
import com.github.mdelapenya.savagedicebot.commands.HelpCommand;
import io.github.cdimascio.dotenv.Dotenv;

import com.github.mdelapenya.savagedicebot.telegram.TelegramBot;
import com.github.mdelapenya.savagedicebot.telegram.TelegramBotListener;

public class SavageDiceBot extends TelegramBot implements TelegramBotListener {

    private static final Dotenv DOTENV = Dotenv.configure().directory(".").load();

    public SavageDiceBot() {
        super( DOTENV.get("TELEGRAM_USERNAME"), DOTENV.get("TELEGRAM_TOKEN"), false, false);
        this.setTelegramBotListener(this);
    }
    
    @Override
    public void receiveMessage(TelegramBotMessage message) {
        //System.out.println(message.getChatID() + " - " + message.getMessageText());

        long chatID = message.getChatID();
        TelegramUser telegramUser = message.getUser();

        if (message.getMessageText() == null) {
            this.sendText(chatID, new HelpCommand("Interaction not supported. Please check my /help").execute());
            return;
        }

        String resultText = new BotInputParser().parse(telegramUser, message.getMessageText());

        if (resultText != null) {
            this.sendText(chatID, resultText);
        }
    }

}
