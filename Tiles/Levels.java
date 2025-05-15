package Tiles;
/**
 * Write a description of class Levels here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Levels {
    public Tile[][] defaultLevel = {
        {new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), },
        {new Tile(TileType.ROAD,  Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), },
        {new Tile(TileType.ROAD,  Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), },
        {new Tile(TileType.ROAD,  Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), new Tile(TileType.GRASS, Direction.NORTH), },
    };
    public Tile[][] loadedLevel;
    
    public void loadFromFile() {
        try {
            File file = new File("filename.txt");
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
