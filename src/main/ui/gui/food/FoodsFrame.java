package ui.gui.food;

import model.food.FoodLogEntry;

import javax.swing.*;
import java.awt.*;

/**
 * FoodsFrame is a frame displayed when the user selects the option to view a food log entry from their food log on
 * FoodLogPanel.
 */

public class FoodsFrame extends JFrame {
    private JPanel totalsPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();
    private FoodsPanel foodsPanel;

    private FoodLogEntry entry;

    private JLabel totalCalories = new JLabel();

    // EFFECTS: initializes FoodsFrame
    public FoodsFrame(FoodLogEntry fe) {
        super("Entry: " + fe.getName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        entry = fe;

        initializeTotalsPanel();

        foodsPanel = new FoodsPanel(fe, this);
        add(foodsPanel);

        setLabels();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the text of labels to the entry's current properties
    public void setLabels() {
        totalCalories.setText("Total calories: " + entry.sumCalories() + " kcal");
    }

    // MODIFIES: this
    // EFFECTS: initializes panel for displaying total calories
    private void initializeTotalsPanel() {
        totalsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 0, 10),
                        BorderFactory.createTitledBorder("Properties")),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        totalsPanel.add(totalCalories, gbc);

        add(totalsPanel);
    }
}
