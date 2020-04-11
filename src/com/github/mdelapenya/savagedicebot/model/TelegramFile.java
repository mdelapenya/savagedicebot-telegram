package com.github.mdelapenya.savagedicebot.model;

public class TelegramFile {

    private String fileId;
    private String downloadFilePath;
    private byte[] fileContent;

    public TelegramFile(String fileId, String downloadFilePath, byte[] fileContent) {
        this.fileId = fileId;
        this.downloadFilePath = downloadFilePath;
        this.fileContent = fileContent;
    }

}
