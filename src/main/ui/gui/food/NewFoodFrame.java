package ui.gui.food;

import exceptions.InvalidInputException;
import model.food.Food;
import model.food.FoodLogEntry;
import model.food.Meal;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * NewFoodFrame is the frame displayed when the user selects the option to create a new food within the selected food
 * log entry on FoodsFrame.
 */

public class NewFoodFrame extends FoodInput {
    private static final int WIDTH = 270;
    private static final int HEIGHT = 350;

    private FoodLogEntry entry;
    private JList<Food> list;
    private DefaultListModel<Food> listModel;
    private FoodsFrame parent;

    public NewFoodFrame(FoodLogEntry entry, JList<Food> list, DefaultListModel<Food> listModel, FoodsFrame parent) {
        super("Create new food");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.entry = entry;
        this.list = list;
        this.listModel = listModel;
        this.parent = parent;

        nameInput();
        mealInput();
        caloriesInput();
        optionalHeading();
        carbsInput();
        fatInput();
        proteinInput();
        addButton("Create");

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Meal mealChosen = (Meal) meal.getSelectedItem();
            int caloriesInt = Integer.parseInt(calories.getText());

            Food f = new Food(name.getText(), mealChosen, caloriesInt);

            trySetMacros(f);

            entry.add(f);
            listModel.addElement(f);
            list.setSelectedValue(f, true);
            parent.setLabels();

            this.dispose();
        } catch (NumberFormatException | InvalidInputException ee) {
            String message = "Please enter a valid input for each field.\n"
                    + "Calories, carbs, fat, and protein should be non-negative integers.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
