package ui.gui.person;

import model.person.ActivityLevel;
import ui.gui.TextInputForm;

import javax.swing.*;
import java.awt.*;

/**
 * UserInfoInput is an abstract class for components that involve the user inputting or selecting options for their
 * personal information.
 */

public abstract class UserInfoInput extends TextInputForm {
    protected JTextField name = new JTextField();
    protected boolean sex = true;
    protected JTextField age = new JTextField();
    protected JTextField height = new JTextField();
    protected JTextField weight = new JTextField();
    protected ActivityLevel activity = ActivityLevel.SEDENTARY;

    // EFFECTS: initializes UserInfoInput
    public UserInfoInput(String name) {
        super(name);
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting user's name
    protected void nameInput() {
        textInput(name, "Name: ", "Enter your name.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for selecting user's biological sex
    protected void sexInput() {
        JLabel label = new JLabel("Sex: ", JLabel.TRAILING);
        label.setToolTipText("Select your biological sex.");

        JPanel optionGroup = new JPanel(new FlowLayout());
        ButtonGroup options = new ButtonGroup();
        setUpSexOptions(optionGroup, options);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(optionGroup, gbc);
    }

    // EFFECTS: creates radio buttons for choosing user's biological sex and adds to given JPanel and ButtonGroup
    private void setUpSexOptions(JPanel optionGroup, ButtonGroup options) {
        JRadioButton male = new JRadioButton("Male", true);
        male.addActionListener(e -> {
            if (male.isSelected()) {
                sex = true;
            }
        });

        JRadioButton female = new JRadioButton("Female");
        female.addActionListener(e -> {
            if (female.isSelected()) {
                sex = false;
            }
        });

        options.add(male);
        options.add(female);
        optionGroup.add(male);
        optionGroup.add(female);
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting user's age
    protected void ageInput() {
        textInput(age, "Age: ", "Enter your age as an integer.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting user's height
    protected void heightInput() {
        textInput(height, "Height (cm): ", "Enter your height in centimeters as an integer.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting user's weight
    protected void weightInput() {
        textInput(weight, "Weight (kg): ", "Enter your weight in kilograms as an integer.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for selecting user's activity level
    protected void activityLevelInput() {
        JLabel label = new JLabel("Activity level: ", JLabel.TRAILING);
        label.setToolTipText("Select your activity level.");

        JPanel optionGroup = new JPanel();
        optionGroup.setLayout(new BoxLayout(optionGroup, BoxLayout.PAGE_AXIS));

        ButtonGroup options = new ButtonGroup();
        setUpActivityOptions(optionGroup, options);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(3, 0, 0, 0);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(optionGroup, gbc);
    }

    // EFFECTS: creates a radio button for each activity level and adds them to given ButtonGroup and JPanel
    private void setUpActivityOptions(JPanel optionGroup, ButtonGroup options) {
        for (ActivityLevel a : ActivityLevel.values()) {
            String name = a.getName();

            JRadioButton button = new JRadioButton(name, true);
            button.addActionListener(e -> {
                if (button.isSelected()) {
                    activity = a;
                }
            });
            button.setToolTipText(a.getDesc());

            options.add(button);
            optionGroup.add(button);
        }
    }
}
