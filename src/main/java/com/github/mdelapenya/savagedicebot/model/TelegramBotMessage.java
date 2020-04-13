package com.github.mdelapenya.savagedicebot.model;

import java.util.ArrayList;

public class TelegramBotMessage {

    private TelegramUser user;
    private long chatID;
    
    private String messageText;
    private ArrayList<TelegramFile> files;
    
    private String command;
    private ArrayList<String> params;

    public TelegramBotMessage(long userID, long chatID, String firstName, String lastName, String userName) {
        this.user = new TelegramUser(userID, firstName, lastName, userName);
        this.chatID = chatID;
        this.messageText = null;
        this.files = null;
        this.command = null;
        this.params = null;
    }

    public TelegramUser getUser() {
        return user;
    }

    public long getChatID() {
        return chatID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public ArrayList<TelegramFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<TelegramFile> files) {
        this.files = files;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "TelegramBotMessage{" + "user=" + user + ", chatID=" + chatID + ", messageText=" + messageText + ", files=" + files + ", command=" + command + ", params=" + params + "}";
    }

}
