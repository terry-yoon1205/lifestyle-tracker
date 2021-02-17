package ui.gui.person;

import model.person.Person;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * LogWeightFrame is the frame displayed when the user selects the option to log a weight change on OverviewPanel.
 * This frame lets the user change their current weight.
 */

public class LogWeightFrame extends UserInfoInput {
    private static final int WIDTH = 270;
    private static final int HEIGHT = 310;

    private Person user;
    private OverviewPanel parent;

    // EFFECTS: initializes LogWeightFrame
    public LogWeightFrame(Person user, OverviewPanel parent) {
        super("Log weight");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.user = user;
        this.parent = parent;

        gbc.gridx = 0;
        gbc.gridy = 0;
        weightInput();
        addButton("Confirm");

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int weightInt = Integer.parseInt(weight.getText());
            user.setWeight(weightInt);

            this.dispose();
            parent.setLabels();
        } catch (NumberFormatException ee) {
            String message = "Please enter a valid input.\nWeight should be a non-negative integer.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
