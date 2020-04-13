package com.github.mdelapenya.savagedicebot;

import com.github.mdelapenya.savagedicebot.model.SavageDiceBot;
import com.github.mdelapenya.savagedicebot.telegram.TelegramBotStarter;

public class Main {

    public static void main(String[] args) throws Exception {
        TelegramBotStarter.start(SavageDiceBot.class);
    }

}
