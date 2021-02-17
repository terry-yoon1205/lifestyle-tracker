package ui.gui;

import model.person.Person;
import persistence.JsonReader;
import ui.gui.person.NewUserFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * LifestyleTrackerAppGUI is a graphic user interface for this application.
 */

public class LifestyleTrackerAppGUI extends JFrame implements ActionListener {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("Welcome to Lifestyle Tracker!");
    private JButton createNew = new JButton("Create new user");
    private JButton load = new JButton("Load user");

    // EFFECTS: initializes LifestyleTrackerApp
    public LifestyleTrackerAppGUI() {
        super("Lifestyle Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        try {
            BufferedImage pic = ImageIO.read(new File("./data/title_image.png"));
            JLabel picLabel = new JLabel(new ImageIcon(pic.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(picLabel);
        } catch (IOException e) {
            // don't load image
        }

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(label);

        initializeButton(createNew, panel);
        createNew.setActionCommand("create new");

        initializeButton(load, panel);
        load.setActionCommand("load");

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes given JButton and adds to the give panel
    private void initializeButton(JButton button, JPanel panel) {
        button.setPreferredSize(new Dimension(150, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        panel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("create new")) {
            String message = "This action will overwrite any existing user file.\nContinue?";
            int n = JOptionPane.showConfirmDialog(this, message, "Warning", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                this.dispose();
                new NewUserFrame();
            }
        } else if (e.getActionCommand().equals("load")) {
            try {
                JsonReader jsonReader = new JsonReader("./data/user.json");
                Person user = jsonReader.read();
                this.dispose();
                new UserActionsFrame(user);
            } catch (IOException ee) {
                JOptionPane.showMessageDialog(this, "File not found.\nPlease create a new user.");
            }
        }
    }
}
