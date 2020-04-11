package com.github.mdelapenya.savagedicebot.telegram;

import com.github.mdelapenya.savagedicebot.telegram.TelegramBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

public class TelegramBotStarter {

    private TelegramBotStarter() {}
    
    public static void start(Class... botClasses) throws Exception {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        for (Class botClass : botClasses) {
            TelegramBot bot=(TelegramBot)botClass.newInstance();
            botsApi.registerBot(bot);
        }
    }

}
