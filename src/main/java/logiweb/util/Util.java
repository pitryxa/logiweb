package logiweb.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Util {
    public static String convertISOToUTF8(String string) throws UnsupportedEncodingException {
        return new String(string.getBytes("ISO-8859-1"), Charset.forName("UTF-8"));
    }
}
