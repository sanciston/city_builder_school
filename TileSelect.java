
/**
 * Tile select menu
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.24
 */

import Tiles.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;


public class TileSelect {
    public boolean visible = false;
    public TileType selectedTile = TileType.GRASS; 
    public Direction selectedDirection = Direction.NORTH;
    
    public final int x = 0;
    public final int y = 0;
    
    public final int closedWidth = 100;
    public final int closedHeight = 100;
    
    public int openTileWidth = 5;
    
    public int openWidth = openTileWidth * Tile.tileSize;
    public int openHeight;
    
    public int tileSize = 100;
    
    public BufferedImage tileSelectIcon = null;


    public TileSelect() {
        try {
            tileSelectIcon = ImageIO.read(new File("Assets/tileselecticon.png"));
        }
        catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
        
        double height = TileType.values().length / openTileWidth;
        openHeight = (int) Math.ceil(height);
    }
}
