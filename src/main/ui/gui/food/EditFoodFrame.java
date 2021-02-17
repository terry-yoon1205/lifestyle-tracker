package ui.gui.food;

import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import model.food.Food;
import model.food.Meal;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * EditFoodFrame is the frame displayed when the user selects the option to edit properties of the food selected on
 * FoodsFrame.
 */

public class EditFoodFrame extends FoodInput {
    private static final int WIDTH = 270;
    private static final int HEIGHT = 350;

    private Food food;
    private FoodsFrame parent;

    // EFFECTS: initializes EditFoodFrame
    public EditFoodFrame(Food f, FoodsFrame parent) {
        super("Edit food");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.food = f;
        this.parent = parent;

        nameInput();
        mealInput();
        caloriesInput();
        optionalHeading();
        carbsInput();
        fatInput();
        proteinInput();
        addButton("Confirm");

        setInitialValues(f);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the initial values of the fields and combo boxes based on given food
    private void setInitialValues(Food f) {
        name.setText(f.getName());
        meal.setSelectedItem(f.getMeal());
        calories.setText(String.valueOf(f.getCalories()));

        try {
            carbs.setText(String.valueOf(f.getCarbs()));
        } catch (NotInitializedException e) {
            // do nothing
        }

        try {
            fat.setText(String.valueOf(f.getFat()));
        } catch (NotInitializedException e) {
            // do nothing
        }

        try {
            protein.setText(String.valueOf(f.getProtein()));
        } catch (NotInitializedException e) {
            // do nothing
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Meal mealChosen = (Meal) meal.getSelectedItem();
            int caloriesInt = Integer.parseInt(calories.getText());

            food.setName(name.getText());
            food.setMeal(mealChosen);
            food.setCalories(caloriesInt);
            trySetMacros(food);

            this.dispose();
            parent.setLabels();
        } catch (NumberFormatException | InvalidInputException ee) {
            String message = "Please enter a valid input for each field.\n"
                    + "Calories, carbs, fat, and protein should be non-negative integers.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
