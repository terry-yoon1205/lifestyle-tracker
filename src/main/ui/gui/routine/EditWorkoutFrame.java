package ui.gui.routine;

import model.routine.MuscleGroup;
import model.routine.Workout;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * EditWorkoutFrame is the frame displayed when the user selects the option to edit the properties of the selected
 * workout on WorkoutsPanel.
 */

public class EditWorkoutFrame extends WorkoutInput {
    private static final int WIDTH = 270;
    private static final int HEIGHT = 350;

    private Workout workout;

    // EFFECTS: initializes EditWorkoutFrame
    public EditWorkoutFrame(Workout w) {
        super("Edit workout");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.workout = w;

        nameInput();
        repsInput();
        setsInput();
        weightInput();
        muscleInput();
        addButton("Confirm");

        name.setText(w.getName());
        reps.setText(String.valueOf(w.getReps()));
        sets.setText(String.valueOf(w.getSets()));
        weight.setText(String.valueOf(w.getWeight()));
        muscle.setSelectedItem(w.getMuscle());

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int repsInt = Integer.parseInt(reps.getText());
            int setsInt = Integer.parseInt(sets.getText());
            int weightInt = Integer.parseInt(weight.getText());
            MuscleGroup muscleGroup = (MuscleGroup) muscle.getSelectedItem();

            workout.setName(name.getText());
            workout.setReps(repsInt);
            workout.setSets(setsInt);
            workout.setWeight(weightInt);
            workout.setMuscle(muscleGroup);

            this.dispose();
        } catch (NumberFormatException ee) {
            String message = "Please enter a valid input for each field.\n"
                    + "Reps, sets, and weight should be integers.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
