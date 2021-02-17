package ui.gui.routine;

import model.routine.MuscleGroup;
import model.routine.Routine;
import model.routine.Workout;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * NewWorkoutFrame is the frame displayed when the user selects the option to create and add a new workout to the
 * selected routine on WorkoutsPanel.
 */

public class NewWorkoutFrame extends WorkoutInput {
    private static final int WIDTH = 270;
    private static final int HEIGHT = 350;

    private Routine routine;
    private JList<Workout> list;
    private DefaultListModel<Workout> listModel;

    // EFFECTS: initializes NewRoutineFrame
    public NewWorkoutFrame(Routine routine, JList<Workout> list, DefaultListModel<Workout> listModel) {
        super("Create new workout");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        this.routine = routine;
        this.list = list;
        this.listModel = listModel;

        nameInput();
        repsInput();
        setsInput();
        weightInput();
        muscleInput();
        addButton("Create");

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

            Workout w = new Workout(name.getText(), repsInt, setsInt, weightInt, muscleGroup);

            routine.add(w);
            listModel.addElement(w);
            list.setSelectedValue(w, true);

            this.dispose();
        } catch (NumberFormatException ee) {
            String message = "Please enter a valid input for each field.\n"
                    + "Reps, sets, and weight should be integers.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
