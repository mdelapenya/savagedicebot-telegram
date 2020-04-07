package org.latruchabot;

import telegrambots.core.TelegramBotStarter;

public class Main
{
    public static void main(String[] args) throws Exception {
        
        TelegramBotStarter.start(LaTruchaBot.class);
    }
}
