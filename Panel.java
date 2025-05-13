
/**
 * Write a description of class Panel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

public class Panel extends JPanel implements Runnable {
    Thread thread;
    
    public Panel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }
    
    public void startThread() {
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    @Override 
    public void run() {
        double drawInterval = 1000000000/60;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
       
        while (thread != null)
        {
            currentTime = System.nanoTime();
           
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
           
            if(delta >= 1) {
                repaint();
                delta--;
                drawCount++;
            }
           
            if(timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
                     
        g2.dispose();
    }
}


