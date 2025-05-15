package Tiles;
/**
 * Write a description of class Tile here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*; 

public class Tile {
    TileType type;
    Direction direction;
    Color color;
    
    public Tile(TileType type, Direction direction) {
        this.type = type;
        this.direction = direction;
        
        switch(type) {
            case ROAD:
            case CURVED_ROAD:  
                this.color = Color.GRAY;
                break;
            case GRASS:
                this.color = Color.GREEN;
                break;
            default:
                this.color = Color.WHITE;
                break;
        }
    }
}
