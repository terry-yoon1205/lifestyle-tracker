package ui.gui.food;

import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import model.person.Person;
import ui.gui.UserActionsFrame;
import ui.gui.person.CustomMacroGoalsFrame;
import ui.gui.person.MacroGoalsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * FoodLogPanel is a component of UserActionsFrame that lets the user perform actions relating to their food log
 * such as adding/removing, viewing, or searching for a food log entry, and also relating to their food goals (calories
 * and macronutrients).
 */

public class FoodLogPanel extends JPanel implements ActionListener {
    private JPanel foodGoalPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();
    private FoodLogListPanel listPanel;

    private Person user;

    private JLabel calorieGoal = new JLabel();
    private JLabel carbsGoal = new JLabel();
    private JLabel fatGoal = new JLabel();
    private JLabel proteinGoal = new JLabel();

    // EFFECTS: initializes FoodLogPanel
    public FoodLogPanel(Person user) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.listPanel = new FoodLogListPanel(user.getFoodLog());
        this.user = user;

        calorieGoal.setHorizontalAlignment(JLabel.LEADING);
        carbsGoal.setHorizontalAlignment(JLabel.LEADING);
        fatGoal.setHorizontalAlignment(JLabel.LEADING);
        proteinGoal.setHorizontalAlignment(JLabel.LEADING);

        initializeFoodGoalPanel();
        setLabels();
        add(listPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets the text of labels to the user's current goals
    public void setLabels() {
        try {
            calorieGoal.setText("Calorie goal: " + user.getCalorieGoal() + " kcal");
        } catch (NotInitializedException e) {
            calorieGoal.setText("Calorie goal: N/A");
        }

        try {
            carbsGoal.setText("Carbs: " + user.getCarbsGoal() + " g");
        } catch (NotInitializedException e) {
            carbsGoal.setText("Carbs: N/A");
        }

        try {
            fatGoal.setText("Fat: " + user.getFatGoal() + " g");
        } catch (NotInitializedException e) {
            fatGoal.setText("Fat: N/A");
        }

        try {
            proteinGoal.setText("Protein: " + user.getProteinGoal() + " g");
        } catch (NotInitializedException e) {
            proteinGoal.setText("Protein: N/A");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel for viewing the user's food goals
    private void initializeFoodGoalPanel() {
        foodGoalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Goals"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addCalorieGoal();
        addMacroGoals();

        add(foodGoalPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds labels and buttons for calorie goal
    private void addCalorieGoal() {
        foodGoalPanel.add(calorieGoal, gbc);

        gbc.gridy++;
        String editGoalText = "Edit calorie goal";
        JButton editGoal = new JButton(editGoalText);
        editGoal.addActionListener(this);
        editGoal.setActionCommand(editGoalText);
        foodGoalPanel.add(editGoal, gbc);

        gbc.gridwidth = 2;
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        foodGoalPanel.add(sep, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds labels and buttons for macronutrient goals
    private void addMacroGoals() {
        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel macroGoalLabel = new JLabel("Macronutrient goals: ", JLabel.LEADING);
        foodGoalPanel.add(macroGoalLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 0, 0);
        foodGoalPanel.add(carbsGoal, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        foodGoalPanel.add(fatGoal, gbc);

        gbc.gridy++;
        foodGoalPanel.add(proteinGoal, gbc);

        gbc.gridy++;
        String editMacroText = "Edit macro goals";
        JButton editGoal = new JButton(editMacroText);
        editGoal.addActionListener(this);
        editGoal.setActionCommand(editMacroText);
        foodGoalPanel.add(editGoal, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Edit calorie goal")) {
            String message = "Would you like to set your calorie goal based on the app's"
                    + "\nrecommendation or your own custom calorie goal?";
            String[] options = {"Recommendation", "Custom"};

            int n = JOptionPane.showOptionDialog(this, message, "Choose an option",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (n == JOptionPane.YES_OPTION) {
                setRecommendedGoal();
            } else if (n == JOptionPane.NO_OPTION) {
                setCustomGoal();
            }
        } else if (e.getActionCommand().equals("Edit macro goals")) {
            String message = "Would you like to set your macro goals based on proportions of"
                    + "\nyour calorie goal or your own custom goals?";
            String[] options = {"Proportions", "Custom"};

            int n = JOptionPane.showOptionDialog(this, message, "Choose an option",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (n == JOptionPane.YES_OPTION) {
                setMacroGoalsProportions();
            } else if (n == JOptionPane.NO_OPTION) {
                new CustomMacroGoalsFrame(user, this);
            }
        }
    }

    // EFFECTS: creates a MacroGoalsFrame to set user's macro goals by proportions of user's calorie goal
    private void setMacroGoalsProportions() {
        try {
            user.getCalorieGoal();
            new MacroGoalsFrame(user, this);
        } catch (NotInitializedException ee) {
            JOptionPane.showMessageDialog(this, "Please set your calorie goal first.");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the user's calorie goal based on recommendation; if no weight goals are set, display message
    private void setRecommendedGoal() {
        String message;
        try {
            user.setCalorieGoal(user.calculateCalories());
            setLabels();
        } catch (NotInitializedException | InvalidInputException ee) {
            message = "In order to get the app's calorie recommendation, please"
                    + "\nset your weight goal and goal intensity first.";
            JOptionPane.showMessageDialog(this, message);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the user's calorie goal based on the user's input
    private void setCustomGoal() {
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Enter your custom calorie goal:",
                "Custom calorie goal",
                JOptionPane.PLAIN_MESSAGE,
                null, null, null);

        if (s != null) {
            try {
                int calorieGoalInt = Integer.parseInt(s);
                user.setCalorieGoal(calorieGoalInt);
                setLabels();
            } catch (NumberFormatException | InvalidInputException ee) {
                JOptionPane.showMessageDialog(this, "Please enter a valid input."
                        + "\nCalories should be a non-negative integer.");
            }
        }
    }
}
