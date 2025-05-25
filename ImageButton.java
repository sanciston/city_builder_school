
/**
 * Image Button class for menus
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */

import java.awt.image.*;
import java.awt.Graphics2D;


public class ImageButton extends Button {
    public BufferedImage image;
    
    public ImageButton(int x, int y, int width, int height, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.image = image;
    };
    
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null);
    }
}
