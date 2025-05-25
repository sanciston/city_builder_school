
/**
 * Button class for menus
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */
public class Button {
    public int x;
    public int y;
    public int width;
    public int height;

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
