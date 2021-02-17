package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * TextInputForm is an abstract class for components that involve the user inputting text information to be saved.
 */

public abstract class TextInputForm extends JFrame implements ActionListener {
    protected JPanel panel = new JPanel(new GridBagLayout());
    protected GridBagConstraints gbc = new GridBagConstraints();

    // EFFECTS: initializes TextInputForm
    public TextInputForm(String name) {
        super(name);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a labelled field with a tooltip
    protected void textInput(JTextField field, String labelText, String toolTip) {
        JLabel label = new JLabel(labelText, JLabel.TRAILING);
        label.setToolTipText(toolTip);
        field.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds a button with given text at the bottom of panel
    protected void addButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 40, 0, 40);
        panel.add(button, gbc);
    }
}
