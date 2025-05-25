package Tiles;
/**
 * Class of tile
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
    
    public float rotation;
    
    public static final int tileSize = 100;
    
    public Tile(TileType type, Direction direction) throws java.io.IOException {
        this.type = type;
        this.direction = direction;
        

        this.image = getImageFromType(type);
        this.rotation = getRotationFromDirection(direction);
    }
    
    public BufferedImage getImageFromType(TileType type) throws java.io.IOException {
        switch(type) {
            case ROAD:
                return ImageIO.read(new File("Assets/road.png"));
            case CURVED_ROAD:  
                return ImageIO.read(new File("Assets/curvedroad.png"));
            case GRASS:
                return ImageIO.read(new File("Assets/grass.png"));
            default:
                return ImageIO.read(new File("Assets/grass.png"));
        }
    }
    
    public float getRotationFromDirection(Direction direction) {
        switch(direction) {
            case NORTH:
                return 0;
            case SOUTH:
                return ((float) Math.PI);
            case WEST:
                return -((float) Math.PI / 2);
            case EAST:
                return ((float) Math.PI / 2);
            default:
                return 0;
        }
    }
}
