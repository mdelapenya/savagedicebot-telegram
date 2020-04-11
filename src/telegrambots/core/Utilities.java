package telegrambots.core;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
