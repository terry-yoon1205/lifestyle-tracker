package ui.gui.routine;

import exceptions.DoesNotExistException;
import model.routine.Routine;
import model.routine.RoutineCollection;
import ui.gui.ListEdit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * RoutinesPanel is a component of UserActionsFrame that lets the user perform actions relating to their routine
 * collection such as adding/removing, viewing, or searching for a routine.
 */

public class RoutinesPanel extends ListEdit<RoutineCollection, Routine> {

    // EFFECTS: initializes RoutinesPanel
    public RoutinesPanel(RoutineCollection rc) {
        super(rc, "Your Workout Routines",
                new String[]{"Edit name", "View routine", "Delete routine"},
                new String[]{"Create a new routine", "Search a routine by name"});
    }

    @Override
    protected void addContents() {
        for (Routine r : collection.getEntries()) {
            listModel.addElement(r);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Edit name":
                editName();
                break;
            case "View routine":
                new WorkoutsFrame(list.getSelectedValue());
                break;
            case "Delete routine":
                deleteRoutine();
                break;
            case "Create a new routine":
                createRoutine();
                break;
            case "Search a routine by name":
                searchRoutine();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the name of the routine with user's given new name
    private void editName() {
        Routine r = list.getSelectedValue();

        String s = (String) JOptionPane.showInputDialog(
                this,
                "Enter the new name:",
                "Edit name",
                JOptionPane.PLAIN_MESSAGE,
                null, null, r.getName());

        if (s != null) {
            r.setName(s);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the selected routine from the routine collection
    private void deleteRoutine() {
        String message = "Are you sure you want to delete this routine\nfrom your routine collection?";
        int n = JOptionPane.showConfirmDialog(this, message, "Warning", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            Routine r = list.getSelectedValue();

            collection.delete(r);
            listModel.removeElement(r);
        }
    }

    // EFFECTS: creates a new routine with name given by user
    private void createRoutine() {
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Name the new routine:",
                "Create new",
                JOptionPane.PLAIN_MESSAGE,
                null, null, null);

        if (s != null) {
            Routine r = new Routine(s);
            collection.add(r);
            listModel.addElement(r);
            list.setSelectedValue(r, true);
            new WorkoutsFrame(r);
        }
    }

    // EFFECTS: searches the routine with given name from the routine collection
    private void searchRoutine() {
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Enter the name of the routine:",
                "Search",
                JOptionPane.PLAIN_MESSAGE,
                null, null, null);

        if (s != null) {
            try {
                Routine r = collection.search(s);
                list.setSelectedValue(r, true);
            } catch (DoesNotExistException ee) {
                JOptionPane.showMessageDialog(this, "Routine with given name does not exist.");
            }
        }
    }
}
