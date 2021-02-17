package ui.gui.routine;

import model.routine.MuscleGroup;
import ui.gui.TextInputForm;

import javax.swing.*;

/**
 * WorkoutInput is an abstract class for components that involve the user inputting and selecting options for properties
 * of a workout.
 */

public abstract class WorkoutInput extends TextInputForm {
    protected JTextField name = new JTextField();
    protected JTextField reps = new JTextField();
    protected JTextField sets = new JTextField();
    protected JTextField weight = new JTextField();
    protected JComboBox<MuscleGroup> muscle = new JComboBox<>();

    // EFFECTS: initializes WorkoutInput
    public WorkoutInput(String name) {
        super(name);
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the workout's name
    protected void nameInput() {
        textInput(name, "Name: ", "Enter the workout's name.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the number of reps per set
    protected void repsInput() {
        textInput(reps, "Reps: ", "Enter the number of repetitions per set.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the number of sets
    protected void setsInput() {
        textInput(sets, "Sets: ", "Enter the number of sets.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for inputting the amount of weight used in the workout
    protected void weightInput() {
        textInput(weight, "Weight (lbs): ", "Enter the amount of weight in pounds as an integer. "
                + "Input 0 for a bodyweight workout.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the row for selecting the main muscle group the workout trains
    protected void muscleInput() {
        JLabel label = new JLabel("Muscle group: ", JLabel.TRAILING);
        label.setToolTipText("Select the main muscle group that the workout trains.");

        for (MuscleGroup mg : MuscleGroup.values()) {
            muscle.addItem(mg);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(muscle, gbc);
    }
}
