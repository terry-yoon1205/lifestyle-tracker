package ui.gui.person;

import exceptions.InvalidInputException;
import model.person.Person;
import ui.gui.TextInputForm;
import ui.gui.food.FoodLogPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * CustomMacroGoalsFrame is the frame displayed when the user selects the option to set their macronutrient goals based
 * on custom amounts in grams.
 */

public class CustomMacroGoalsFrame extends TextInputForm {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 220;

    private Person user;
    private FoodLogPanel parent;

    private JTextField carbs = new JTextField();
    private JTextField fat = new JTextField();
    private JTextField protein = new JTextField();

    public CustomMacroGoalsFrame(Person user, FoodLogPanel parent) {
        super("Set custom macro goals");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.user = user;
        this.parent = parent;

        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        JLabel header = new JLabel("Enter the goal amount in grams.");
        panel.add(header, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 0);

        textInput(carbs, "Carbs: ", "Enter the goal amount of carbs in grams.");
        textInput(fat, "Fat: ", "Enter the goal amount of fat in grams.");
        textInput(protein, "Protein: ", "Enter the goal amount of protein in grams.");
        addButton("Confirm");

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int carbsInt = Integer.parseInt(carbs.getText());
            int fatInt = Integer.parseInt(fat.getText());
            int proteinInt = Integer.parseInt(protein.getText());

            user.setCarbsGoal(carbsInt);
            user.setFatGoal(fatInt);
            user.setProteinGoal(proteinInt);

            this.dispose();
            parent.setLabels();
        } catch (NumberFormatException | InvalidInputException ee) {
            JOptionPane.showMessageDialog(this, "Please enter a valid input for each field."
                    + "\nCarbs, fat, and protein must be a non-negative integer.");
        }
    }
}
