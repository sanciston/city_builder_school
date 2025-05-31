
/**
 * The menu when ESC is pressed
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.awt.Color;

public class PauseMenu extends Menu {
    ImageButton saveGame;
    PauseMenuState state;
        
    public PauseMenu(int screenWidth, int screenHeight) throws java.io.IOException {
        saveGame = new ImageButton((screenWidth - 300) / 2, 100, 300, 100, ImageIO.read(new File("Assets/savegame.png")));
        loadGame = new ImageButton((screenWidth - 300) / 2, 250, 300, 100, ImageIO.read(new File("Assets/loadgame.png")));
        state = PauseMenuState.MAIN;
    }
}
