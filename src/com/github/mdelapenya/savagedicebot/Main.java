package com.github.mdelapenya.savagedicebot;

import telegrambots.core.TelegramBotStarter;

public class Main {

    public static void main(String[] args) throws Exception {
        TelegramBotStarter.start(SavageDiceBot.class);
    }

}
