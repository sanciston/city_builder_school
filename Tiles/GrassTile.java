package Tiles;


/**
 * Write a description of class GrassTile here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*; 

public class GrassTile extends Tile{
    public GrassTile() {
        this.type = TileType.GRASS;
        this.color = Color.green;
    }
}
