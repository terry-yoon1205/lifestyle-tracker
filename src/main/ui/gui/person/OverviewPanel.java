package ui.gui.person;

import exceptions.NotInitializedException;
import model.person.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * OverviewPanel is a component of UserActionsFrame that lets the user view and edit their personal information or
 * perform actions related to their weight goal: add/edit their weight goals, log a weight change, view their progress.
 */

public class OverviewPanel extends JPanel implements ActionListener {
    private Person user;
    private JPanel profile = new JPanel(new GridBagLayout());
    private JPanel weightProgress = new JPanel(new GridBagLayout());

    private JLabel name = new JLabel();
    private JLabel sex = new JLabel();
    private JLabel age = new JLabel();
    private JLabel height = new JLabel();
    private JLabel activity = new JLabel();

    private JLabel currentWeight = new JLabel();
    private JLabel goalWeight = new JLabel();
    private JLabel goalIntensity = new JLabel();

    // EFFECTS: initializes OverviewPanel
    public OverviewPanel(Person user) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.user = user;
        initializeProfile();
        initializeWeightProgress();
        setLabels();
    }

    // MODIFIES: this
    // EFFECTS: sets the text of labels to the user's current information
    public void setLabels() {
        name.setText(user.getName());
        sex.setText(user.getSexString());
        age.setText("Age: " + user.getAge());
        height.setText("Height: " + user.getHeight() + " cm");
        activity.setText("Activity level: " + user.getActivityLevel().getName());
        currentWeight.setText("Current weight: " + user.getWeight() + " kg");

        try {
            goalWeight.setText("Goal weight: " + user.getWeightGoal() + " kg");
        } catch (NotInitializedException e) {
            goalWeight.setText("Goal weight: N/A");
        }

        try {
            goalIntensity.setText("Goal intensity: " + user.getGoalIntensity().getDesc());
        } catch (NotInitializedException e) {
            goalIntensity.setText("Goal intensity: N/A");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel for user's profile
    private void initializeProfile() {
        profile.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Profile"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        profileAddLabels(gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        JButton editProfile = new JButton("Edit...");
        editProfile.addActionListener(this);
        editProfile.setActionCommand("Edit user");
        profile.add(editProfile, gbc);

        add(profile);
    }

    // EFFECTS: adds labels for user info in profile panel
    private void profileAddLabels(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        profile.add(name, gbc);

        gbc.gridy++;
        profile.add(sex, gbc);

        gbc.gridy++;
        profile.add(age, gbc);

        gbc.gridy++;
        profile.add(height, gbc);

        gbc.gridy++;
        profile.add(activity, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel for user's weight goal progress
    private void initializeWeightProgress() {
        weightProgress.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Weight Progress"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        weightProgressAddLabels(gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 0, 0);
        JButton logWeight = new JButton("Log weight change");
        logWeight.addActionListener(this);
        logWeight.setActionCommand("Log");
        weightProgress.add(logWeight, gbc);


        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        JButton editGoals = new JButton("Edit goals");
        editGoals.addActionListener(this);
        editGoals.setActionCommand("Edit goals");
        weightProgress.add(editGoals, gbc);

        add(weightProgress);
    }

    // EFFECTS: adds labels for user info in weight progress panel
    private void weightProgressAddLabels(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        weightProgress.add(currentWeight, gbc);

        gbc.gridy++;
        weightProgress.add(goalWeight, gbc);

        gbc.gridy++;
        weightProgress.add(goalIntensity, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Edit user":
                new EditUserFrame(user, this);
                break;
            case "Log":
                new LogWeightFrame(user, this);
                break;
            case "Edit goals":
                new EditWeightGoalsFrame(user, this);
                break;
        }
    }
}
