
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
    
    class WindowCanvas extends Canvas {
        public WindowCanvas (int width, int height) {
            setBackground (Color.GRAY);
            setSize(width, height);
        }

        public void paint (Graphics graphics) {
            super.paint(graphics);
            if(offScreenImage == null) {
                offScreenImage = new BufferedImage(get)
            }            
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D
        }
    }
    
    WindowCanvas canvas;
    
    public Window(String title, int width, int height) {
        setTitle(title);
        this.getContentPane().setPreferredSize(new Dimension(width, height)); 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.toFront(); 
        this.setVisible(true);
                    
        this.canvas = new WindowCanvas(width, height);
        
        this.add(this.canvas);
        
        this.pack();
    }
    
}
