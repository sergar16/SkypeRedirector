package console;

import skype.SkypeController;
import ui.ComboCellInsetsDemo;
import utils.IO;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by Serhii on 11/2/2015.
 */
public class Runner {
    public static void main(String[] args) {
        try {
            final String login = args[0];
            final String password = args[1];
            SkypeController skypeController = SkypeController.getInstance();
            skypeController.signIn(login, password);
            skypeController.addRedirectEventListener();
            ComboCellInsetsDemo.getInstance().createAndShowGUI();
            ComboCellInsetsDemo.getInstance().setDataTable(IO.openFile(getDefautSaveFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getDefautSaveFile() {
        try {
            System.out.println(getJarFilePath());
            return new File(getJarFilePath() + "save");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
return null;
    }

    public static String getJarFilePath() throws URISyntaxException {
        String jarPath=Runner.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        return    jarPath.substring(0,jarPath.lastIndexOf("/")+1);


    }
}
