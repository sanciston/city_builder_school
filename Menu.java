
/**
 * Menus, many menus.
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.awt.Color;

public class Menu {
    ImageButton loadGame;
    ArrayList<TextButton> saves = new ArrayList<TextButton>();
        
    public void getSaves(String savesFolder) {
        File folder = new File(savesFolder);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    saves.add(new TextButton(0, i * 150, 1600, 100, 100, listOfFiles[i].getName(), new Color(255, 255, 255), new Color(0, 0, 0)));
                    System.out.println(listOfFiles[i].getName());
                } 
            }
        }
    }
}
