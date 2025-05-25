
/**
 * Image Button class for menus
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */

import java.awt.image.*;
import java.awt.*;


public class TextButton extends Button {
    public String text;
    public Color backgroundColor;
    public Color textColor;
    public int textSize;
    
    public TextButton(int x, int y, int width, int height, int textSize, String text, Color backgroundColor, Color textColor) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.textSize = textSize;
    };
    
    public void draw(Graphics2D g2) {
        g2.setColor(backgroundColor);
        g2.fillRect(x, y, width, height);
        g2.setColor(textColor);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, textSize)); 
        g2.drawString(text, x, y + height - (textSize / 5));
    }
}
