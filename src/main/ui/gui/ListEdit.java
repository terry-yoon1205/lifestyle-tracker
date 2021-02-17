package ui.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * ListEdit is an abstract class for GUI components that involve displaying a list in a scrollable pane and actions on
 * that list such as adding/removing an element, searching for an element, or viewing/editing an element.
 */

public abstract class ListEdit<T, E> extends JPanel implements ListSelectionListener, ActionListener {
    protected static final int ROW_COUNT = 10;

    protected T collection;

    protected JPanel listPanel = new JPanel();
    protected JPanel actionsPanel = new JPanel();
    protected JList<E> list;
    protected DefaultListModel<E> listModel;

    protected List<JButton> selectionButtons = new ArrayList<>();
    protected List<JButton> listButtons = new ArrayList<>();

    // EFFECTS: initializes ListEdit
    public ListEdit(T collection, String borderName, String[] selectionButtons, String[] listButtons) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(borderName),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        this.collection = collection;

        for (String s : selectionButtons) {
            JButton button = new JButton(s);
            button.addActionListener(this);
            button.setActionCommand(s);
            this.selectionButtons.add(button);
        }

        for (String s : listButtons) {
            JButton button = new JButton(s);
            button.addActionListener(this);
            button.setActionCommand(s);
            this.listButtons.add(button);
        }

        initializeListPanel();
        initializeListButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel for the list and actions on its selection
    protected void initializeListPanel() {
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.LINE_AXIS));

        listContents();
        JScrollPane scrollPane = new JScrollPane(list);
        listPanel.add(scrollPane);

        initializeSelectionButtonPanel();

        add(listPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up list
    protected void listContents() {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(ROW_COUNT);

        addContents();
    }

    // MODIFIES: this
    // EFFECTS: adds contents to the list
    protected abstract void addContents();

    // MODIFIES: this
    // EFFECTS: initializes panel containing buttons to view or delete selected routine in given JPanel
    protected void initializeSelectionButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (JButton button : selectionButtons) {
            button.setEnabled(false);
            panel.add(button);
        }

        listPanel.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel containing buttons for user actions to the routine collection
    protected void initializeListButtonPanel() {
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.PAGE_AXIS));
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (JButton button : listButtons) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            actionsPanel.add(button);
        }

        add(actionsPanel);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (list.getSelectedIndex() == -1) {
                for (JButton button : selectionButtons) {
                    button.setEnabled(false);
                }
            } else {
                for (JButton button : selectionButtons) {
                    button.setEnabled(true);
                }
            }
        }
    }
}
