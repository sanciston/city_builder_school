
/**
 * Write a description of class Window here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame  {
    public Window(String title, int width, int height) {
        setTitle(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.toFront(); 
        this.setVisible(true);
        
        Panel panel = new Panel(width, height);
        this.add(panel);
        
        this.pack();
        
        panel.startThread();
    }
    
}
