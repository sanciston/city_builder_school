package Tiles;

import java.util.ArrayList;

public class TileManager {
    Tile[][] tiles;
    int width;
    int height;
    
    Levels levels = new Levels();
    public TileManager() {
        tiles = levels.defaultLevel;
        this.width = tiles[0].length;
        this.height = tiles.length;
    }
}
