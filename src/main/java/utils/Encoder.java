package utils;



import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by Serhii on 10/29/2015.
 */
public class Encoder {
    public static String encode(String string) {
        try {
            //   char[] chars = (char[]) string.getBytes("windows-1251");
            return new String(string.getBytes(), "windows-1251");
        } catch (UnsupportedEncodingException uee) {
        }
        return null;
    }
}
