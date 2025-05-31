
/**
 * The menu when the game loads.
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.*;

public class MainMenu extends Menu {    
    MainMenuState state;
    ImageButton newGame;
    BufferedImage logo;
        
    public MainMenu(int screenWidth, int screenHeight) throws java.io.IOException {
        loadGame = new ImageButton((screenWidth - 300) / 2, 350, 300, 100, ImageIO.read(new File("Assets/loadgame.png")));
        newGame = new ImageButton((screenWidth - 300) / 2, 500, 300, 100, ImageIO.read(new File("Assets/newgame.png")));
        logo = ImageIO.read(new File("Assets/logo.png"));
        state = MainMenuState.MAIN;
    }
}
