package ui;

import model.Chat;
import model.ComboboxItem;
import model.SkypeComboboxItem;
import model.SlackComboboxItem;
import skype.SkypeController;
import utils.slack.SlackController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Serhii on 10/28/2015.
 */
public class ComboBoxAutocomplete {

    public static ArrayList<ComboboxItem> lookupItem(String pattern) {
        // iterate over all items
        //TODO ignore case
      return Chat.getListofAllChats()
                .stream()
              .sorted((e1,e2)->e1.toString().compareTo(e2.toString()))
              .filter(comboboxItem1 -> comboboxItem1.toString()!=null)
                .filter(comboboxItem -> comboboxItem.toString()
                        .startsWith(pattern))
              .collect(Collectors.toCollection(ArrayList::new));
}

    public static void complete(final JComboBox jComboBox,final String pattern) {
        DefaultComboBoxModel model = new DefaultComboBoxModel( lookupItem(pattern).toArray() );
        jComboBox.setModel( model );
    }




}
