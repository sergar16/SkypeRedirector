package console;

import skype.SkypeController;
import ui.ComboCellInsetsDemo;
import utils.IO;

/**
 * Created by Serhii on 11/2/2015.
 */
public class Runner {
    public static void main(String[] args) {
        try {

            final String login=args[0];
            final String password=args[1];
            SkypeController skypeController = SkypeController.getInstance();
            skypeController.signIn(login, password);
            skypeController.addRedirectEventListener();
            ComboCellInsetsDemo.getInstance().createAndShowGUI();
            //IO.openFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
