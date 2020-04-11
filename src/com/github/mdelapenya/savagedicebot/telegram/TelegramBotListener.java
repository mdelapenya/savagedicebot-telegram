package com.github.mdelapenya.savagedicebot.telegram;

import com.github.mdelapenya.savagedicebot.model.TelegramBotMessage;

public interface TelegramBotListener {

    void receiveMessage(TelegramBotMessage message);

}
