
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
    public TileType selectedTile = TileType.GRASS; 
    public Direction selectedDirection = Direction.NORTH;
    public HouseType selectedHouse = HouseType.RESIDENTIAL;
    
    public final int x = 0;
    public final int y = 0;
    
    public final int closedWidth = 100;
    public final int closedHeight = 100;
    
    public boolean open = false;
    public boolean houseSelected = false;
    
    public int openTileWidth = 5;
    
    public int openWidth = openTileWidth * Tile.tileSize;
    public int openHeight;
    
    public int tileSize = 100;
    


    public TileSelect() {
        double height = TileType.values().length / openTileWidth;
        openHeight = (int) Math.ceil(height);
    }
}
