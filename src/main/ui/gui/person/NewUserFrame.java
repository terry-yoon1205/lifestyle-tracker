package ui.gui.person;

import model.person.Person;
import ui.gui.UserActionsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * NewUserFrame is a component of LifestyleTrackerAppGUI for creating a new user. This frame creates a new Person as
 * the user for the application.
 */

public class NewUserFrame extends UserInfoInput {
    private static final int WIDTH = 270;
    private static final int HEIGHT = 350;

    // EFFECTS: initializes NewUserFrame
    public NewUserFrame() {
        super("Create new user");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        nameInput();
        sexInput();
        ageInput();
        heightInput();
        weightInput();
        activityLevelInput();
        addButton("Create");

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String name = this.name.getText();
            int ageInt = Integer.parseInt(age.getText());
            int weightInt = Integer.parseInt(weight.getText());
            int heightInt = Integer.parseInt(height.getText());

            Person p = new Person(name, sex, ageInt, heightInt, weightInt, activity);
            this.dispose();
            new UserActionsFrame(p);
        } catch (NumberFormatException ee) {
            String message = "Please enter a valid input for each field.\n"
                    + "Age, height, and weight should be integers.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
