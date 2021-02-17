package ui.gui.food;

import exceptions.DoesNotExistException;
import model.food.FoodLog;
import model.food.FoodLogEntry;
import ui.gui.ListEdit;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static model.person.Person.DATE_FORMAT;

/**
 * FoodLogListPanel is a component of FoodLogPanel that lets the user perform actions relating to their food log
 * such as adding/removing, viewing, or searching for a food log entry.
 */

public class FoodLogListPanel extends ListEdit<FoodLog, FoodLogEntry> {

    // EFFECTS: initializes FoodLogListPanel
    public FoodLogListPanel(FoodLog fl) {
        super(fl, "Your Food Log",
                new String[]{"Edit date", "View entry", "Delete entry"},
                new String[]{"Create a new entry", "Search an entry by date"});
    }

    @Override
    protected void addContents() {
        for (FoodLogEntry fe : collection.getEntries()) {
            listModel.addElement(fe);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Edit date":
                editDate();
                break;
            case "View entry":
                new FoodsFrame(list.getSelectedValue());
                break;
            case "Delete entry":
                deleteEntry();
                break;
            case "Create a new entry":
                createEntry();
                break;
            case "Search an entry by date":
                searchEntry();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the name of the entry with user's given new name
    private void editDate() {
        FoodLogEntry fe = list.getSelectedValue();

        String s = (String) JOptionPane.showInputDialog(
                this,
                "Enter the new date (" + DATE_FORMAT + "):",
                "Edit date",
                JOptionPane.PLAIN_MESSAGE,
                null, null, fe.getName());

        if (s != null) {
            fe.setName(s);
        }
    }

    // EFFECTS: deletes the selected entry from the food log
    private void deleteEntry() {
        String message = "Are you sure you want to delete this entry\nfrom your food log?";
        int n = JOptionPane.showConfirmDialog(this, message, "Warning", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            FoodLogEntry fe = list.getSelectedValue();

            collection.delete(fe);
            listModel.removeElement(fe);
        }
    }

    // EFFECTS: creates a new food log entry with the date given by user
    private void createEntry() {
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Enter the date for the new entry (" + DATE_FORMAT + "):",
                "Create new",
                JOptionPane.PLAIN_MESSAGE,
                null, null, null);

        if (s != null) {
            FoodLogEntry fe = new FoodLogEntry(s);
            collection.add(fe);
            listModel.add(0, fe);
            list.setSelectedValue(fe, true);
            new FoodsFrame(fe);
        }
    }

    // EFFECTS: searches the entry with given date from the food log
    private void searchEntry() {
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Enter the date you wish to search:",
                "Search",
                JOptionPane.PLAIN_MESSAGE,
                null, null, null);

        if (s != null) {
            try {
                FoodLogEntry fe = collection.search(s);
                list.setSelectedValue(fe, true);
            } catch (DoesNotExistException ee) {
                JOptionPane.showMessageDialog(this, "Entry with given date does not exist.");
            }
        }
    }
}
