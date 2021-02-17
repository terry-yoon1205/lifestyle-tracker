package ui.gui;

import model.person.Person;
import persistence.JsonWriter;
import ui.gui.food.FoodLogPanel;
import ui.gui.person.OverviewPanel;
import ui.gui.routine.RoutinesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * UserActionsFrame is a component of LifestyleTrackerAppGUI for the user to perform actions such as editing their
 * profile, editing their weight or calorie goals, and viewing and editing their routine collection or their food log.
 */

public class UserActionsFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 630;

    private Person user;
    private JTabbedPane panel = new JTabbedPane();
    private OverviewPanel overview;
    private RoutinesPanel routines;
    private FoodLogPanel foodLog;

    // EFFECTS: initializes UserActionFrame
    public UserActionsFrame(Person user) {
        super("Lifestyle Tracker");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        this.user = user;
        overview = new OverviewPanel(user);
        routines = new RoutinesPanel(user.getRoutines());
        foodLog = new FoodLogPanel(user);

        panel.addTab("Overview", overview);
        panel.addTab("Routines", routines);
        panel.addTab("Food Log", foodLog);

        add(panel);

        JButton save = new JButton("Save user");
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setMargin(new Insets(0, 20, 0, 20));
        save.addActionListener(this);
        add(save);
        add(Box.createRigidArea(new Dimension(5, 15)));

        // pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JsonWriter jsonWriter = new JsonWriter("./data/user.json");
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Successfully saved!");
        } catch (FileNotFoundException ee) {
            JOptionPane.showMessageDialog(this, "Unable to write file.");
        }
    }
}
