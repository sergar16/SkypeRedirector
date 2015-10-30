package ui;

/**
 * Created by Serhii on 10/26/2015.
 */

import model.ComboboxItem;
import model.RedirectRecord;
import skype.SkypeController;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.stream.IntStream;

public class ComboCellInsetsDemo {
    private static ComboCellInsetsDemo instance = null;

    private final static int WINDOW_WIDTH = 400;
    private final static int WINDOW_HEIGHT = 500;
    protected final static int COMBOBOX_WIDTH = 150;
    private final static int CHECKBOX_COLUMN_WIDTH = 40;
    private final static int ROW_HEIGHT = 40;

    public static ComboCellInsetsDemo getInstance() {
        if (instance == null) {
            instance = new ComboCellInsetsDemo();
        }
        return instance;
    }

    JTable table;

    public JComponent makeUI() {
        String[] columnNames = {"From", "To", "Double Direction"};
        Object[][] data = {
                {"", "", false}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table = new JTable(model);
        table.setRowHeight(ROW_HEIGHT);
        table.setAutoCreateRowSorter(true);
        TableColumn columnFrom = table.getColumnModel().getColumn(0);
        TableColumn columnTo = table.getColumnModel().getColumn(1);
        TableColumn columnDD = table.getColumnModel().getColumn(2);
        columnFrom.setCellRenderer(new ComboBoxCellRenderer());
        columnFrom.setCellEditor(new ComboBoxCellEditor());

        columnTo.setCellRenderer(new ComboBoxCellRenderer());
        columnTo.setCellEditor(new ComboBoxCellEditor());

        columnDD.setWidth(CHECKBOX_COLUMN_WIDTH);
        columnDD.setCellRenderer(new CheckboxCellRenderer());
        columnDD.setCellEditor(new CheckBoxCellEditor());

        return new JScrollPane(table);
    }

    public void addRow() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"", "", false});
    }

    public void removeSelectedRows() {
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        int[] rows = table.getSelectedRows();
        for(int i=0;i<rows.length;i++){
            model.removeRow(rows[i]-i);
        }
    }

    public ArrayList<RedirectRecord> getDataTable() {
        ArrayList<RedirectRecord> redirectRecordArrayList = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            final String from = table.getValueAt(i, 0).toString();
            final String to = table.getValueAt(i, 1).toString();
            final boolean dd = (Boolean) table.getValueAt(i, 2);
            redirectRecordArrayList.add(new RedirectRecord(from, to, dd));
        }
        return redirectRecordArrayList;
    }


    public void setDataTable(ArrayList<RedirectRecord> redirectRecords) {
        prepareBeforeLoading(redirectRecords.size());
        for (int i = 0; i < redirectRecords.size(); i++) {
            table.setValueAt(redirectRecords.get(i).from, i, 0);
            table.setValueAt(redirectRecords.get(i).to, i, 1);
            table.setValueAt(redirectRecords.get(i).doubleDirection, i, 2);
        }
        ((DefaultTableModel) table.getModel()).fireTableDataChanged();
    }

    public void prepareBeforeLoading(int capacity) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        //remove all rows
        IntStream.range(0, defaultTableModel.getRowCount() - 1).forEach(rowIndex -> defaultTableModel.removeRow(0));
        for (int i = 0; i < capacity - 1; i++) addRow();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(ComboCellInsetsDemo.getInstance().makeUI());
        f.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
        f.setLocationRelativeTo(null);
        f.setJMenuBar(MenuBar.getMenuBar());
        f.setVisible(true);
    }


    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
        table.updateUI();
        table.update(table.getGraphics());
        ((DefaultTableModel) table.getModel()).fireTableDataChanged();
    }
}


class ComboBoxPanel extends JPanel {
    private ComboboxItem[] items = SkypeController.getSortedArrayOfGroupAndUsers();
    final protected JComboBox<ComboboxItem> comboBox = new JComboBox<ComboboxItem>(items) {
        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            return new Dimension(ComboCellInsetsDemo.COMBOBOX_WIDTH, d.height);
        }
    };

    public ComboBoxPanel() {
        super();
        setOpaque(true);
        comboBox.setEditable(true);
        comboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                                                                     @Override
                                                                     public void keyReleased(KeyEvent event) {
                                                                         JTextComponent textComponent = ((JTextComponent) ((JComboBox) ((Component) event
                                                                                 .getSource()).getParent()).getEditor()
                                                                                 .getEditorComponent());
                                                                         String pattern = textComponent.getText();

                                                                         if (pattern != "") {
                                                                             ComboBoxAutocomplete.complete(comboBox, pattern);
                                                                             textComponent.setText(pattern);
                                                                             comboBox.showPopup();
                                                                         }
                                                                     }
                                                                 }

        );

        add(comboBox);
    }
}

class CheckBoxPanel extends JPanel {
    final protected JCheckBox checkBox = new JCheckBox();

    public CheckBoxPanel() {
        super();
        setOpaque(true);
        add(checkBox);
    }
}


class ComboBoxCellRenderer extends ComboBoxPanel
        implements TableCellRenderer {
    public ComboBoxCellRenderer() {
        super();
        setName("Table.cellRenderer");
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        setBackground(isSelected ? table.getSelectionBackground()
                : table.getBackground());
        if (value != null) {
            comboBox.setSelectedItem(value);
        }
        return this;
    }
}

class CheckboxCellRenderer extends CheckBoxPanel
        implements TableCellRenderer {
    public CheckboxCellRenderer() {
        super();
        setName("Table.cellRenderer");
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        setBackground(isSelected ? table.getSelectionBackground()
                : table.getBackground());
        checkBox.setSelected(Boolean.parseBoolean(value.toString()));
        return this;
    }
}

class ComboBoxCellEditor extends ComboBoxPanel
        implements TableCellEditor {
    public ComboBoxCellEditor() {
        super();
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        this.setBackground(table.getSelectionBackground());
        comboBox.setSelectedItem(value);
        return this;
    }

    //Copid from DefaultCellEditor.EditorDelegate
    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            MouseEvent e = (MouseEvent) anEvent;
            return e.getID() != MouseEvent.MOUSE_DRAGGED;
        }
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        if (comboBox.isEditable()) {
            comboBox.actionPerformed(new ActionEvent(this, 0, ""));
        }
        fireEditingStopped();
        return true;
    }

    //Copid from AbstractCellEditor
    //protected EventListenerList listenerList = new EventListenerList();
    transient protected ChangeEvent changeEvent = null;

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }

    public CellEditorListener[] getCellEditorListeners() {
        return listenerList.getListeners(CellEditorListener.class);
    }

    protected void fireEditingStopped() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                // Lazily create the event:
                if (changeEvent == null) changeEvent = new ChangeEvent(this);
                ((CellEditorListener) listeners[i + 1]).editingStopped(changeEvent);
            }
        }
    }

    protected void fireEditingCanceled() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                // Lazily create the event:
                if (changeEvent == null) changeEvent = new ChangeEvent(this);
                ((CellEditorListener) listeners[i + 1]).editingCanceled(changeEvent);
            }
        }
    }

}