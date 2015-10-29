package ui;

import model.ComboboxItem;
import skype.SkypeController;

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
      return   SkypeController.getSortedListOfGroupAndUsers()
                .stream()
                .filter(comboboxItem -> comboboxItem.toString().startsWith(pattern))
              .collect(Collectors.toCollection(ArrayList::new));
}

    public static void complete(final JComboBox jComboBox,final String pattern) {
        DefaultComboBoxModel model = new DefaultComboBoxModel( lookupItem(pattern).toArray() );
        jComboBox.setModel( model );
    }




}
