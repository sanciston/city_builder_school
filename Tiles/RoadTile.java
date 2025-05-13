package Tiles;
/**
 * Write a description of class GrassTile here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*; 

public class RoadTile extends Tile{
    public RoadTile() {
        this.type = TileType.ROAD;
        this.color = Color.gray;
    }
}
