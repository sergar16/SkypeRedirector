package utils;

import javax.swing.*;

/**
 * Created by Serhii on 10/30/2015.
 */
public class Icon {
    public static ImageIcon SKYPE_ICON = utils.Icon.createImageIcon("/skype.png");
    public static ImageIcon SLACK_ICON = utils.Icon.createImageIcon("/slack.png");

    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Icon.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
