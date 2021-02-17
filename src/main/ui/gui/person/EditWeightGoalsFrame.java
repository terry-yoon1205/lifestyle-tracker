package ui.gui.person;

import exceptions.InvalidInputException;
import model.person.GoalIntensity;
import model.person.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static model.person.GoalIntensity.*;

/**
 * EditWeightGoalsFrame is the frame displayed when the user selects the option to edit their weight goals (goal weight
 * and/or intensity) on OverviewPanel.
 */

public class EditWeightGoalsFrame extends UserInfoInput {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 280;

    private Person user;
    private OverviewPanel parent;

    private JTextField goalWeight;
    private GoalIntensity goalIntensity = EXTREME_LOSS;

    // EFFECTS: initializes LogWeightFrame
    public EditWeightGoalsFrame(Person user, OverviewPanel parent) {
        super("Edit Goals");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.user = user;
        this.parent = parent;

        goalWeightInput();
        goalIntensityInput();
        addButton("Confirm");

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting user's goal weight
    private void goalWeightInput() {
        JLabel label = new JLabel("Goal weight: ", JLabel.TRAILING);
        label.setToolTipText("Enter the weight you wish to achieve.");
        goalWeight = new JTextField();
        goalWeight.addActionListener(this);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(goalWeight, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting user's goal intensity
    private void goalIntensityInput() {
        JLabel label = new JLabel("Goal intensity: ", JLabel.TRAILING);
        label.setToolTipText("Select the speed at which you wish to achieve your goal.");

        JPanel optionGroup = new JPanel();
        optionGroup.setLayout(new BoxLayout(optionGroup, BoxLayout.PAGE_AXIS));

        ButtonGroup options = new ButtonGroup();
        setUpIntensityOptions(optionGroup, options);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(3, 0, 0, 0);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(optionGroup, gbc);
    }

    // EFFECTS: creates a radio button for each goal intensity and adds them to given ButtonGroup and JPanel
    private void setUpIntensityOptions(JPanel optionGroup, ButtonGroup options) {
        for (GoalIntensity g : GoalIntensity.values()) {
            String desc = g.getDesc();

            JRadioButton button = new JRadioButton(desc, true);
            button.addActionListener(e -> {
                if (button.isSelected()) {
                    goalIntensity = g;
                }
            });

            options.add(button);
            optionGroup.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int goalWeightInt = Integer.parseInt(goalWeight.getText());
            user.setWeightGoal(goalWeightInt);
            user.setGoalIntensity(goalIntensity);

            this.dispose();
            parent.setLabels();
        } catch (NumberFormatException | InvalidInputException ee) {
            String message = "Please enter a valid input for each field."
                    + "\nWeight should be a non-negative integer.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
