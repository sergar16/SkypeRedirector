package utils;

import model.RedirectRecord;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Serhii on 10/28/2015.
 */
public class Serializer {

    public static void serializeObject(Serializable object, File out) {
        if (!out.exists()) {
            try {
                out.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            out.delete();
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream fos = new FileOutputStream(out);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<RedirectRecord> unSerializeObject(File in) {
        if (in.exists()) {
            try (FileInputStream fis = new FileInputStream(in);
                 ObjectInputStream oos = new ObjectInputStream(fis)) {
                return (ArrayList<RedirectRecord>) oos.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}