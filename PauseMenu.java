
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

public class PauseMenu {
    ImageButton saveGame;
    ImageButton loadGame;
    ArrayList<TextButton> saves = new ArrayList<TextButton>();
    
    PauseMenuState state;
        
    public PauseMenu(int screenWidth, int screenHeight) throws java.io.IOException {
        saveGame = new ImageButton((screenWidth - 300) / 2, 100, 300, 100, ImageIO.read(new File("Assets/savegame.png")));
        loadGame = new ImageButton((screenWidth - 300) / 2, 250, 300, 100, ImageIO.read(new File("Assets/loadgame.png")));
    }
    
    public void getSaves(String savesFolder) {
        File folder = new File(savesFolder);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    saves.add(new TextButton(0, i * 150, 500, 100, 100, listOfFiles[i].getName(), new Color(255, 255, 255), new Color(0, 0, 0)));
                    System.out.println(listOfFiles[i].getName());
                } 
             }
        }
    }
}
