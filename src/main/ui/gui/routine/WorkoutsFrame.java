package ui.gui.routine;

import model.routine.Routine;

import javax.swing.*;

/**
 * WorkoutsFrame is a frame displayed when the user selects the option to view a routine from their routine collection
 * on RoutinesPanel.
 */

public class WorkoutsFrame extends JFrame {
    private WorkoutsPanel panel;

    // EFFECTS: initializes WorkoutsFrame
    public WorkoutsFrame(Routine routine) {
        super("Routine: " + routine.getName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panel = new WorkoutsPanel(routine);
        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
