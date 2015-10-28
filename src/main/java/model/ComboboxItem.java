package model;

import com.samczsun.skype4j.chat.Chat;
import skype.SkypeController;

/**
 * Created by Serhii on 10/26/2015.
 */
public class ComboboxItem {
    private String name;
    private Chat value;
    private static SkypeController skypeController = SkypeController.getInstance();

    public ComboboxItem(Chat value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return skypeController.getChatName(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Chat getValue() {
        return value;
    }

    public void setValue(Chat value) {
        this.value = value;
    }

    public static SkypeController getSkypeController() {
        return skypeController;
    }

    public static void setSkypeController(SkypeController skypeController) {
        ComboboxItem.skypeController = skypeController;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComboboxItem) {
            if (((ComboboxItem) obj).toString().equals(this.toString())) return true;
        }
        return false;
    }
}
