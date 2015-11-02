package utils;

import model.RedirectRecord;
import ui.ComboCellInsetsDemo;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Serhii on 10/28/2015.
 */
public class IO {
   public static ArrayList<RedirectRecord> openFile(File file){
       return Serializer.unSerializeObject(file);
   }
    public static void saveFile(File file){
        Serializer.serializeObject(ComboCellInsetsDemo.getInstance().getDataTable(), file);
    }
    public static ArrayList<RedirectRecord> openFileMenu() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
           return openFile(fileChooser.getSelectedFile());
        }
        return null;
    }



    public static void saveAsMenu() {
        final JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
          saveFile(fileChooser.getSelectedFile());
        }
    }
}
