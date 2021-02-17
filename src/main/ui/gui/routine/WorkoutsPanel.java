package ui.gui.routine;

import exceptions.DoesNotExistException;
import model.routine.Routine;
import model.routine.Workout;
import ui.gui.ListEdit;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * WorkoutsPanel is the main component of WorkoutsFrame that lets the user add/remove, view/edit, or search for a
 * workout from their selected routine.
 */

public class WorkoutsPanel extends ListEdit<Routine, Workout> {

    // EFFECTS: initializes WorkoutsPanel
    public WorkoutsPanel(Routine r) {
        super(r, r.getName(),
                new String[]{"Edit...", "Delete..."},
                new String[]{"Add a new workout", "Search a workout by name"});

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createTitledBorder(r.getName())),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    @Override
    protected void addContents() {
        for (Workout w : collection.getUnits()) {
            listModel.addElement(w);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Edit...":
                new EditWorkoutFrame(list.getSelectedValue());
                break;
            case "Delete...":
                deleteWorkout();
                break;
            case "Add a new workout":
                new NewWorkoutFrame(collection, list, listModel);
                break;
            case "Search a workout by name":
                searchWorkout();
                break;
        }
    }

    // EFFECTS: deletes the selected workout from the routine
    private void deleteWorkout() {
        String message = "Are you sure you want to delete this workout\nfrom your routine?";
        int n = JOptionPane.showConfirmDialog(this, message, "Warning", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            Workout w = list.getSelectedValue();

            collection.delete(w);
            listModel.removeElement(w);
        }
    }

    // EFFECTS: searches the workout with given name from the routine
    private void searchWorkout() {
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Enter the name of the workout:",
                "Search",
                JOptionPane.PLAIN_MESSAGE,
                null, null, null);

        if (s != null) {
            try {
                Workout w = collection.search(s);
                list.setSelectedValue(w, true);
            } catch (DoesNotExistException ee) {
                JOptionPane.showMessageDialog(this, "Workout with given name does not exist.");
            }
        }
    }
}
