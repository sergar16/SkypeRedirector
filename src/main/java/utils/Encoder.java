package utils;


import org.apache.commons.lang.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by Serhii on 10/29/2015.
 */
public class Encoder {

    public static String encode(String string) {
        try {
         //   return new String(string.getBytes(), "utf-8");
           String s="";
            UniversalDetector encDetector = new UniversalDetector(null);
            encDetector.handleData(string.getBytes(), 0, string.getBytes().length);
            encDetector.dataEnd();
            String encoding = encDetector.getDetectedCharset();
            System.out.println(encoding);
            if (encoding != null) s = new String(string.getBytes(encoding), "utf-8");
            else return string;

            System.out.println(s);
            return s;
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return null;
    }
}
