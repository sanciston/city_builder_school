package Tiles;
/**
 * Class for tiles
 *
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.24
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
    
    public Tile(TileType type, Direction direction) throws java.io.IOException {
        this.type = type;
        this.direction = direction;
    }
}
