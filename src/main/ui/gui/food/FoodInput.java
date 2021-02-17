package ui.gui.food;

import exceptions.InvalidInputException;
import model.food.Food;
import model.food.Meal;
import ui.gui.TextInputForm;

import javax.swing.*;
import java.awt.*;

/**
 * FoodInput is an abstract class for components that involve the user inputting and selecting options for properties of
 * a food.
 */

public abstract class FoodInput extends TextInputForm {
    protected JTextField name = new JTextField();
    protected JTextField calories = new JTextField();
    protected JTextField carbs = new JTextField();
    protected JTextField fat = new JTextField();
    protected JTextField protein = new JTextField();
    protected JComboBox<Meal> meal = new JComboBox<>();

    // EFFECTS: initializes FoodInput
    public FoodInput(String name) {
        super(name);
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the food's name
    protected void nameInput() {
        textInput(name, "Name: ", "Enter the name of the food.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the food's amount of calories
    protected void caloriesInput() {
        textInput(calories, "Calories (kcal): ", "Enter the amount of calories.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for selecting the meal the food was eaten as
    protected void mealInput() {
        JLabel label = new JLabel("Meal: ", JLabel.TRAILING);
        label.setToolTipText("Select the meal the food was eaten as.");

        for (Meal m : Meal.values()) {
            meal.addItem(m);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(meal, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds a label as a heading to indicate the fields below as optional and a separator
    protected void optionalHeading() {
        JLabel label = new JLabel("Optional: ");

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(label, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the food's amount of carbs
    protected void carbsInput() {
        textInput(carbs, "Carbs (g): ", "Enter the amount of carbohydrates.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the food's amount of fat
    protected void fatInput() {
        textInput(fat, "Fat (g): ", "Enter the amount of fat.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the food's amount of protein
    protected void proteinInput() {
        textInput(protein, "Protein (g): ", "Enter the amount of protein");
    }

    // EFFECTS: try setting the given food's macros based on user input; do nothing if empty string given,
    //          throw InvalidInputException if a negative integer, or NumberFormatException if not an integer
    protected void trySetMacros(Food f) throws NumberFormatException, InvalidInputException {
        if (!carbs.getText().equals("")) {
            int carbsInt = Integer.parseInt(carbs.getText());
            f.setCarbs(carbsInt);
        } else {
            f.removeCarbs();
        }

        if (!fat.getText().equals("")) {
            int fatInt = Integer.parseInt(fat.getText());
            f.setFat(fatInt);
        } else {
            f.removeFat();
        }

        if (!protein.getText().equals("")) {
            int proteinInt = Integer.parseInt(protein.getText());
            f.setProtein(proteinInt);
        } else {
            f.removeProtein();
        }
    }
}
