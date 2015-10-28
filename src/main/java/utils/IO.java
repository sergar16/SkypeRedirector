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
  public static ArrayList<RedirectRecord> openFile(){
     JFileChooser fileChooser=new JFileChooser();
       if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           // load from file
          return (ArrayList<RedirectRecord>)Serializer.unSerializeObject(file);

       }
    return  null;}

    public static void saveAs() {
        final JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Serializer.serializeObject(ComboCellInsetsDemo.getInstance().getDataTable(),file);

        }
    }
}
