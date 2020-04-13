package com.github.mdelapenya.savagedicebot.telegram;

import java.io.File;
import java.util.ArrayList;

import com.github.mdelapenya.savagedicebot.model.TelegramBotMessage;
import com.github.mdelapenya.savagedicebot.model.TelegramFile;
import com.github.mdelapenya.savagedicebot.Utilities;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class TelegramBot extends TelegramLongPollingBot {

    public static String TEMP_DIR = null;
    
    private String botUsername;
    private String botToken;
    private TelegramBotListener telegramBotListener;
    
    private boolean allowImages;
    private boolean allowDocuments;
    
    public TelegramBot(String botUsername, String botToken, boolean allowImages, boolean allowDocuments) {
        if(TelegramBot.TEMP_DIR == null) {
            String tempDir = System.getProperty("user.home") + "\\temp\\telegramBots";
            new File(tempDir).mkdirs();
            TelegramBot.TEMP_DIR = tempDir.replace("\\", "/");
        }
        
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.allowImages = allowImages;
        this.allowDocuments = allowDocuments;
        this.telegramBotListener = null;
        
        System.out.println("Starting " + botUsername);
    }

    @Override
    public final String getBotUsername() {
        return botUsername;
    }
    
    @Override
    public final String getBotToken()
    {
        return botToken;
    }

    public void setTelegramBotListener(TelegramBotListener telegramBotListener) {
        this.telegramBotListener = telegramBotListener;
    }

    @Override
    public final void onUpdateReceived(final Update update) {
        Thread thread = new Thread(() -> {
            try {
                TelegramBotMessage message = new TelegramBotMessage(
                    update.getMessage().getFrom().getId(),
                    update.getMessage().getChatId(),
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getLastName(),
                    update.getMessage().getFrom().getUserName()
                );

                if(telegramBotListener!= null && update.hasMessage()) {
                    if(update.getMessage().hasText()) {
                        message.setMessageText(update.getMessage().getText());

                        if(message.getMessageText().startsWith("/")) {
                            String data[] = message.getMessageText().split(" ");
                            String cmd = null;
                            ArrayList<String> params = new ArrayList<>();

                            for(String dt : data) {
                                if(cmd == null) {
                                    cmd = dt;
                                } else {
                                    params.add(dt);
                                }
                            }

                            message.setCommand(cmd);
                            message.setParams(params);
                        }
                    }

                    if(allowImages && update.getMessage().hasPhoto()) {
                        message.setFiles(Utilities.getPhotos(this, update));
                    }

                    if(allowDocuments && update.getMessage().hasDocument()) {
                        ArrayList<TelegramFile> files = new ArrayList<>();
                        files.add(Utilities.getDocument(this, update));
                        message.setFiles(files);
                    }

                    telegramBotListener.receiveMessage(message);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        thread.start();
    }
    
    public final void sendText(final long chatId, final String text) {
        Thread thread = new Thread(() -> {
            try {
                SendMessage message = new SendMessage().setParseMode("HTML").setChatId(chatId).setText(text);
                execute(message);
            } catch (Exception e) {
            }
        });
        
        thread.start();
    }

}
