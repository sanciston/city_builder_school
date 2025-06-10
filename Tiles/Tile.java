package Tiles;
/**
 * Class for tiles
 *
 * AUTHOR: Brendan Laking
 * VERSION: 2025.06.10
 */

import java.awt.*; 
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;


public class Tile {
    public TileType type;
    public Direction direction;
    
    public BufferedImage image;    
    public static final int tileSize = 100;
    
    public boolean solid;
    
    public Tile(TileType type, Direction direction, int x, int y) throws java.io.IOException {
        this.type = type;
        this.direction = direction;    
        
        if(type == TileType.ROAD || type == TileType.CURVED_ROAD) {
            solid = false;
        } else {
            solid = true;
        }
    }
}
