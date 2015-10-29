package ui;

import com.samczsun.skype4j.chat.GroupChat;
import skype.SkypeController;

import javax.swing.*;

/**
 * Created by Serhii on 10/26/2015.
 */
@Deprecated
public class Combobox {
    public static JComboBox generateBox() {
        final JComboBox bx = new JComboBox();
        SkypeController skypeController = SkypeController.getInstance();
        skypeController.getAllGroupChats().stream().forEach(chat -> {
            bx.addItem(((GroupChat) chat).getTopic());
        });
        skypeController.getUsers().stream().forEach(usr -> {
            bx.addItem(usr.getUsername());
        });

        return bx;

    }
}
