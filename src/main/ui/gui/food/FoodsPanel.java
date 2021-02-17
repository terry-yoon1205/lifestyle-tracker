package ui.gui.food;

import exceptions.DoesNotExistException;
import model.food.Food;
import model.food.FoodLogEntry;
import model.food.Meal;
import ui.gui.ListEdit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * FoodsPanel is the main component of FoodsFrame that lets the user add/remove, view/edit, or search for a
 * food from their selected food log entry.
 */

public class FoodsPanel extends ListEdit<FoodLogEntry, Food> implements ItemListener {
    private FoodsFrame parent;

    private JComboBox<Meal> mealFilter = new JComboBox<>();

    // EFFECTS: initializes FoodsPanel
    public FoodsPanel(FoodLogEntry fe, FoodsFrame parent) {
        super(fe, fe.getName(),
                new String[]{"Edit...", "Delete..."},
                new String[]{"Add a new food", "Search a food by name"});

        this.parent = parent;

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(0, 10, 10, 10),
                        BorderFactory.createTitledBorder(fe.getName())),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        addFilterOptions();
    }

    // MODIFIES: this
    // EFFECTS: adds an option to filter foods by meal
    private void addFilterOptions() {
        JPanel filterPanel = new JPanel(new FlowLayout());
        JLabel filterLabel = new JLabel("Filter by meal: ");

        mealFilter.addItem(null);
        for (Meal m : Meal.values()) {
            mealFilter.addItem(m);
        }
        mealFilter.addItemListener(this);

        filterPanel.add(filterLabel);
        filterPanel.add(mealFilter);
        actionsPanel.add(filterPanel);
    }

    @Override
    protected void addContents() {
        for (Food f : collection.getUnits()) {
            listModel.addElement(f);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Edit...":
                new EditFoodFrame(list.getSelectedValue(), parent);
                break;
            case "Delete...":
                deleteFood();
                break;
            case "Add a new food":
                new NewFoodFrame(collection, list, listModel, parent);
                break;
            case "Search a food by name":
                searchFood();
                break;
        }
    }

    // EFFECTS: deletes the selected food from the entry
    private void deleteFood() {
        String message = "Are you sure you want to delete this food\nfrom your food log entry?";
        int n = JOptionPane.showConfirmDialog(this, message, "Warning", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            Food f = list.getSelectedValue();

            collection.delete(f);
            listModel.removeElement(f);
            parent.setLabels();
        }
    }

    // EFFECTS: searches the food with given name from the entry
    private void searchFood() {
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Enter the name of the food:",
                "Search",
                JOptionPane.PLAIN_MESSAGE,
                null, null, null);

        if (s != null) {
            try {
                Food f = collection.search(s);
                list.setSelectedValue(f, true);
            } catch (DoesNotExistException ee) {
                JOptionPane.showMessageDialog(this, "Food with given name does not exist.");
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Meal selected = (Meal) mealFilter.getSelectedItem();

        listModel.clear();
        if (selected != null) {
            for (Food f : collection.getFoodsByMeal(selected)) {
                listModel.addElement(f);
            }
        } else {
            addContents();
        }
    }
}
