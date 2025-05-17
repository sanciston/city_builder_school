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
import java.io.IOException; 

public class Level {
    
    public Tile[][] tiles;
    
    int width;
    int height;
    
    String levelName;
    
    public Level(String fileName) {   
        loadFromFile(fileName);
        
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                System.out.print(tiles[x][y].type);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
    
    //Function loadFromFile loads the file into the level class. Returns false if it can't load, returns true if it can load
    public boolean loadFromFile(String fileName) {
        String data = null;
        
        File file = new File(fileName);  
        
        if(!file.isFile()) {
            return false;
        }
            
        try {
            Scanner scanner = new Scanner(file);
            
            int line = 0;
            //Scan each line and get the data for the level.
            while (scanner.hasNextLine()) {
                if(data == null) { 
                    data = "";
                }
                
                data = scanner.nextLine();
                
                //Line zero stores the information about level width and height in the format WidthxHeight.
                if(line == 0) {
                    String string = "";
                    int i = 0;
                    
                    //Get the width data up until the x seperator.
                    for(i = 0; data.charAt(i) != 'x'; i++) {
                        string = string + data.charAt(i);
                    }
                    
                    //Convert that data into intergers 
                    try {
                       this.width = Integer.parseInt(string);
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }


                    string = ""; //Reset the string.
                    i++; //Skip past the x seperator.
                    
                    //Get the height data up until the newline.
                    while(i < data.length()) {
                        string = string + data.charAt(i);
                        i++;
                    }

                    //Convert that data into intergers    .      
                    try {
                       this.height = Integer.parseInt(string);
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                    
                    //now that we know the width and height create tiles accordingly.
                    this.tiles = new Tile[this.width][this.height]; 
                } else if(line == 1) { //Line 1 has the level name.
                    this.levelName = data;
                } else if(line < this.height + 2) {
                    //The tile data is formatted in alternated charecters representing tile type and tile dircetion. 
                    for(int i = 0; i < this.width; i++) {
                        char typeChar = data.charAt(i * 2); //even numbers will be represent the type. 
                        char directionChar = data.charAt((i * 2) + 1); //odd numbers will be represent the dircetion.
                        
                        Direction direction; 
                        TileType tileType;

                        //Give the tile its type.
                        if(typeChar == 'R') {
                            tileType = TileType.ROAD;
                        } else if(typeChar == 'G') {
                            tileType = TileType.GRASS;
                        } else if(typeChar == 'C') {
                            tileType = TileType.CURVED_ROAD;
                        } else {
                            tileType = TileType.UNKNOWN;
                        } 
                        
                        //Give the tile its direction.
                        if(directionChar == 'N') {
                            direction = Direction.NORTH;
                        } else if(directionChar == 'E') {
                            direction = Direction.EAST;
                        } else if(directionChar == 'S') {
                            direction = Direction.SOUTH;
                        } else {
                            direction = Direction.WEST; //an unkown direction seems pointless so it'll default west for funnies
                        }
                        
                        tiles[i][line - 2] = new Tile(tileType, direction);
                    }
                }
                line++;
            }
            
            scanner.close(); 
            
        } catch(IOException e) {
            return false;
        }
        
        return true;
    }
}
