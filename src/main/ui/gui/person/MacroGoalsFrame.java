package ui.gui.person;

import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import model.person.Person;
import ui.gui.TextInputForm;
import ui.gui.UserActionsFrame;
import ui.gui.food.FoodLogPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * MacroGoalsFrame is the frame displayed when the user selects the option to set their macronutrient goals based on
 * proportions of their calorie goal on FoodLogPanel.
 */

public class MacroGoalsFrame extends TextInputForm {
    private static final int WIDTH = 320;
    private static final int HEIGHT = 250;

    private Person user;
    private FoodLogPanel parent;

    private JTextField carbs = new JTextField();
    private JTextField fat = new JTextField();
    private JTextField protein = new JTextField();

    // EFFECTS: initializes MacroGoalsFrame
    public MacroGoalsFrame(Person user, FoodLogPanel parent) {
        super("Set macro goals by proportions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.user = user;
        this.parent = parent;

        gbc.gridwidth = 2;
        JLabel header = new JLabel("Enter a proportion between 0 and 1 (e.g. 0.5).");
        JLabel secondHeader = new JLabel("All three proportions must add up to 1.");
        panel.add(header, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(secondHeader, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 0);

        textInput(carbs, "Carbs: ", "Enter the goal proportion of carbohydrates in your daily calories consumed.");
        textInput(fat, "Fat: ", "Enter the goal proportion of fat in your daily calories consumed.");
        textInput(protein, "Protein: ", "Enter the goal proportion of protein in your daily calories consumed.");
        addButton("Confirm");

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double carbsDouble = Double.parseDouble(carbs.getText());
            double fatDouble = Double.parseDouble(fat.getText());
            double proteinDouble = Double.parseDouble(protein.getText());

            user.setMacroGoal(carbsDouble, fatDouble, proteinDouble);

            this.dispose();
            parent.setLabels();
        } catch (NumberFormatException | InvalidInputException | NotInitializedException ee) {
            JOptionPane.showMessageDialog(this, "Please enter a valid input for each field."
                    + "\nThe three proportions must be decimals that add up to 1.");
        }
    }
}
