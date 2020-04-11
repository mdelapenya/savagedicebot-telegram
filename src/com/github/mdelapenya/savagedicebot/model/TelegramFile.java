package com.github.mdelapenya.savagedicebot.model;

import java.io.FileInputStream;
import java.util.ArrayList;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import sun.misc.IOUtils;
import com.github.mdelapenya.savagedicebot.telegram.TelegramBot;

public class TelegramFile {

    private String fileId;
    private String downloadFilePath;
    private byte[] fileContent;

    private TelegramFile(String fileId, String downloadFilePath, byte[] fileContent) {
        this.fileId = fileId;
        this.downloadFilePath = downloadFilePath;
        this.fileContent = fileContent;
    }
    
    public static ArrayList<TelegramFile> getPhotos(TelegramBot bot, Update update) {
        ArrayList<TelegramFile> res = new ArrayList<>();
        
        try {
            for(PhotoSize ps : update.getMessage().getPhoto()) {
                String fileId = ps.getFileId();
                String downloadFilePath;
                byte[] fileContent; 
                
                GetFile getFileRequest = new GetFile();
                getFileRequest.setFileId(fileId);
                org.telegram.telegrambots.api.objects.File file = bot.getFile(getFileRequest);

                downloadFilePath = file.getFilePath();
                java.io.File fileFromSystem =bot.downloadFile(downloadFilePath);

                fileContent = IOUtils.readFully(new FileInputStream(fileFromSystem), -1, true);

                res.add(new TelegramFile(fileId, downloadFilePath, fileContent));
            }
        } catch (Exception e) {
            res = null;
        }
        
        return res;
    }
    
    public static TelegramFile getDocument(TelegramBot bot, Update update) {
        try {
            Document doc = update.getMessage().getDocument();

            String fileId = doc.getFileId();
            
            String downloadFilePath;
            byte[] fileContent;

            GetFile getFileRequest = new GetFile();
            getFileRequest.setFileId(fileId);
            org.telegram.telegrambots.api.objects.File file = bot.getFile(getFileRequest);

            downloadFilePath = file.getFilePath();
            java.io.File fileFromSystem =bot.downloadFile(downloadFilePath);

            fileContent = IOUtils.readFully(new FileInputStream(fileFromSystem), -1, true);
            
            return new TelegramFile(fileId, downloadFilePath, fileContent);
        } catch (Exception e) {
            return null;
        }
    }

}
