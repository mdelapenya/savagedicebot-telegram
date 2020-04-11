package com.github.mdelapenya.savagedicebot;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.github.mdelapenya.savagedicebot.model.TelegramFile;
import com.github.mdelapenya.savagedicebot.telegram.TelegramBot;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import sun.misc.IOUtils;

public class Utilities {

    private static String LAST_TIME_STAMP = "";

    public static synchronized String timeStamp() {
        String formato = "yyyyMMddHHmmssSSS";

        String res = new SimpleDateFormat(formato).format(new Date());

        while (res.equals(LAST_TIME_STAMP)) {
            res = new SimpleDateFormat(formato).format(new Date());
        }

        LAST_TIME_STAMP = res;

        return res;
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

    public static byte[] getURL(String _url) {
        byte res[] = null;

        try {
            InputStream is = null;

            _url = _url.replaceAll(" ", "%20");

            if (_url.substring(0, 7).equalsIgnoreCase("file://")) {
                is = new FileInputStream(_url.substring(7));
            } else {
                URL miURL = new URL(_url);

                is = miURL.openStream();
            }

            res = IOUtils.readFully(is, -1, true);
        } catch (Exception e) {
            res = null;
        }

        return res;
    }

}
