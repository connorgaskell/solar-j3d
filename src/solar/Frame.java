package solar;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    
    /*
     * Default frame dimensions
     */
    private final int frameWidth = 650;
    private final int frameHeight = 500;
    
    public Frame() {
        // Some code that's required for Java3D to work within a JFrame.
        System.setProperty("sun.awt.noerasebackground", "true");
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
        
        // Set the JFrame layout to BorderLayout
        setLayout(new BorderLayout());

        // Set the title of the application
        setTitle("JSolar 3D");

        // Set the minimum size of the window
        setMinimumSize(new Dimension(frameWidth, frameHeight));

        // Set the initial size of the window
        setSize(1150, 750);

        // Centre the window relative to the screen
        setLocationRelativeTo(null);

        // Close the window when the user presses the close button
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create an object of Game (Java3D Canvas) and add it to the frame
        getContentPane().add(new Solar(), BorderLayout.CENTER);

        // Set the window visible
        setVisible(true);
    }

}
