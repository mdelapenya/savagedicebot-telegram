package telegrambots.core;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class TelegramBot extends TelegramLongPollingBot
{
    public static String TEMP_DIR = null;
    
    private String botUsername;
    private String botToken;
    private TelegramBotListener telegramBotListener;
    
    private boolean allowImages;
    private boolean allowDocuments;
    
    public TelegramBot(String botUsername, String botToken, boolean allowImages, boolean allowDocuments)
    {
        if(TelegramBot.TEMP_DIR == null)
        {
            String tempDir = System.getProperty("user.home") + "\\temp\\telegramBots";
            new File(tempDir).mkdirs();
            TelegramBot.TEMP_DIR = tempDir.replace("\\", "/");
        }
        
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.allowImages = allowImages;
        this.allowDocuments = allowDocuments;
        this.telegramBotListener = null;
        
        System.out.println("Iniciando " + botUsername);
    }

    @Override
    public final String getBotUsername()
    {
        return this.botUsername;
    }
    
    @Override
    public final String getBotToken()
    {
        return this.botToken;
    }

    public void setTelegramBotListener(TelegramBotListener telegramBotListener)
    {
        this.telegramBotListener = telegramBotListener;
    }

    @Override
    public final void onUpdateReceived(final Update update)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    TelegramBotMessage message = new TelegramBotMessage(update.getMessage().getFrom().getId(),
                                                                        update.getMessage().getChatId(),
                                                                        update.getMessage().getFrom().getFirstName(),
                                                                        update.getMessage().getFrom().getLastName(),
                                                                        update.getMessage().getFrom().getUserName());
                    
                    if(TelegramBot.this.telegramBotListener!= null && update.hasMessage())
                    {
                        if(update.getMessage().hasText())
                        {
                            message.setMessageText(update.getMessage().getText());
                            
                            if(message.getMessageText().startsWith("/"))
                            {
                                String data[] = message.getMessageText().split(" ");
                                String cmd = null;
                                ArrayList<String> params = new ArrayList<>();

                                for(String dt : data)
                                {
                                    if(cmd == null)
                                    {
                                        cmd = dt;
                                    }
                                    else
                                    {
                                        params.add(dt);
                                    }
                                }

                                message.setCommand(cmd);
                                message.setParams(params);
                            }
                        }
                        
                        if(TelegramBot.this.allowImages && update.getMessage().hasPhoto())
                        {
                            message.setFiles(TelegramFile.getPhotos(TelegramBot.this, update));
                        }
                        
                        if(TelegramBot.this.allowDocuments && update.getMessage().hasDocument())
                        {
                            ArrayList<TelegramFile> files = new ArrayList<>();
                            files.add(TelegramFile.getDocument(TelegramBot.this, update));
                            message.setFiles(files);
                        }
                        
                        TelegramBot.this.telegramBotListener.reciveMessage(message);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        
        thread.start();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final void sendText(TelegramUser user, final String text)
    {
        this.sendText(user.getId(), text);
    }
    
    public final void sendText(final long chatId, final String text)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    SendMessage message = new SendMessage().setParseMode("HTML").setChatId(chatId).setText(text);
                    TelegramBot.this.execute(message);
                }
                catch (Exception e)
                {
                }
            }
        };
        
        thread.start();
    }
    
    public final void sendImage(TelegramUser user, final Object imagePath, final String text)
    {
        this.sendImage(user.getId(), imagePath, text);
    }
    
    public final void sendImage(final long chatId, final Object imagePath, final String text)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Object img = imagePath;
                    
                    File imgPath = null;
                    
                    if(img instanceof String)
                    {
                        String path = (String)img;
                        
                        if(path.toLowerCase().startsWith("http"))
                            img = new URL(path);
                        else
                            img = new File((String)img);
                    }
                    
                    
                    if(img instanceof File)
                    {
                        imgPath = (File)img;
                    }
                    else if(img instanceof URL)
                    {
                        URL url = (URL)img;
                        
                        String tempFile = TelegramBot.TEMP_DIR + "/" + Utilities.timeStamp();
                        
                        try(FileOutputStream fos = new FileOutputStream(tempFile))
                        {
                            fos.write(Utilities.getURL(url.toString()));
                        }
                        
                        imgPath = new File(tempFile);
                    }
                    else
                    {
                        return;
                    }
                    
                    
                    SendPhoto photo = new SendPhoto().setChatId(chatId).setNewPhoto(imgPath);
                    
                    if(text != null) {
                        photo.setCaption(text);
                    }
                    
                    TelegramBot.this.sendPhoto(photo);
                }
                catch (Exception e)
                {
                }
            }
        };
        
        thread.start();
    }
}