
/**
 * Basic JFrame functionality 
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    public Window(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Makes it so that when the close button is presed on the window it closes.

        setResizable(false); 
        
        //New Panel for drawing.
        Panel panel = new Panel(width, height); 
        add(panel);
        
        pack();
        
        setVisible(true);
        
        toFront(); 
        
        panel.startThread(); //Start the thread for the panel to draw.   
    }
}
