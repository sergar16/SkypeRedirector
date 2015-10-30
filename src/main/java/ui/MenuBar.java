package ui;

import utils.IO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Serhii on 10/28/2015.
 */
public class MenuBar {
    static JMenuBar getMenuBar() {
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

//a group of JMenuItems
        menuItem = new JMenuItem("Open");
        menuItem.setMnemonic(KeyEvent.VK_D);
        menuItem.addActionListener(e -> ComboCellInsetsDemo.getInstance().setDataTable(IO.openFile()));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Save redirect configuration");
        menuItem.addActionListener(e -> IO.saveAs());
        menu.add(menuItem);

        menuItem = new JMenuItem("Save as",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

//Build second menu in the menu bar.
        menu = new JMenu("Table");
        menuItem = new JMenuItem("Add Row");
        menuItem.addActionListener(e -> ComboCellInsetsDemo.getInstance().addRow());

        menu.add(menuItem);
        menuItem = new JMenuItem("Delete Row(s)");
        menuItem.addActionListener(e -> ComboCellInsetsDemo.getInstance().removeSelectedRows());
        menu.add(menuItem);
        menuBar.add(menu);
        return menuBar;
    }


}
