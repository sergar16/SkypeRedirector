package ui;

import model.ComboboxItem;
import utils.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Serhii on 10/30/2015.
 */
class ComboBoxRenderer extends JLabel
        implements ListCellRenderer {
    private Font uhOhFont;

    public ComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
    }


    /*
         * This method finds the image and text corresponding
         * to the selected value and returns the label, set up
         * to display the text and image.
         */
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        //Get the selected index. (The index param isn't
        //always valid, so just use the value.)
        // int selectedIndex = ((Integer)value).intValue();

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Set the icon and text.  If icon was null, say so.
        switch (((ComboboxItem) value).getProgram()) {
            case SKYPE:
                setIcon(utils.Icon.SKYPE_ICON);
                break;
            case SLACK:
                setIcon(utils.Icon.SLACK_ICON);

        }
        setText(value.toString());
        return this;
    }
}