package ui.gui.person;

import model.person.Person;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * EditUserFrame is a frame displayed when the user selects the option to edit their personal information on
 * OverviewPanel, and it lets the user edit their personal information except weight.
 */

public class EditUserFrame extends UserInfoInput {
    private static final int WIDTH = 270;
    private static final int HEIGHT = 310;

    private Person user;
    private OverviewPanel parent;

    // EFFECTS: initializes EditUserFrame
    public EditUserFrame(Person user, OverviewPanel parent) {
        super("Edit Info");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.user = user;
        this.parent = parent;

        nameInput();
        sexInput();
        ageInput();
        heightInput();
        activityLevelInput();
        addButton("Confirm");

        name.setText(user.getName());
        age.setText(String.valueOf(user.getAge()));
        height.setText(String.valueOf(user.getHeight()));

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String name = this.name.getText();
            int ageInt = Integer.parseInt(age.getText());
            int heightInt = Integer.parseInt(height.getText());

            user.setName(name);
            user.setSex(sex);
            user.setAge(ageInt);
            user.setHeight(heightInt);
            user.setActivityLevel(activity);

            this.dispose();
            parent.setLabels();
        } catch (NumberFormatException ee) {
            String message = "Please enter a valid input for each field.\nAge and weight should be integers.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
