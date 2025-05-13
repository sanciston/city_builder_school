package Tiles;

import java.util.ArrayList;

public class TileManager {
    Tile[][] tiles;
    int width;
    int height;
    
    Levels levels = new Levels();
    public TileManager(int width, int height) {
        tiles = levels.defaultLevel;
    }
}
