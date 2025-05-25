package Tiles;
/**
 * Manages level loading and saving
 *
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.24
 */
import java.io.*;
import java.util.Scanner; 


public class Level {
    
    public Tile[][] tiles;
    
    public int width;
    public int height;
    
    String levelName;
    
    public String loadedFile = "";
    
    public Level(String fileName) { 
        loadedFile = fileName;
        loadFromFile(fileName);
    }
    
    //Function loadFromFile loads the file into the level class. Returns false if it can't load, returns true if it can load
    public boolean loadFromFile(String fileName) {
        loadedFile = fileName;

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
                    //The tile data is formatted in alternated comma seperated values representing tile type and tile dircetion. This is a data storage format called DICSV (Dumb Idiot's CSV).
        
                    int charNumber = 0;
                    for(int i = 0; i < this.width; i++) {
                        String typeString = ""; 
                        String directionString = "";
                        
                        Direction direction; 
                        TileType type;
                        
                        //Get type data until we reach a comma.
                        while(data.charAt(charNumber) != ',') {
                            typeString += String.valueOf(data.charAt(charNumber));
                            charNumber++;
                        }                        


                        charNumber++; //Skip the comma.
                        
                        //Get direction data until we reach a comma.
                        while(data.charAt(charNumber) != ',') {
                            directionString += String.valueOf(data.charAt(charNumber));
                            charNumber++;
                        }
                        
                        charNumber++; //Skip the comma.
                        
                        //Set the type to corresponding enumerated value.
                        if(typeString.equals("GRASS")) { 
                            type = TileType.GRASS;
                        } else if (typeString.equals("ROAD")) {
                            type = TileType.ROAD;
                        } else if (typeString.equals("CURVED_ROAD")) {
                            type = TileType.CURVED_ROAD;
                        } else {
                            type = null;
                        }
                        
                        //Set the direction to corresponding enumerated value.
                        if(directionString.equals("NORTH")) { 
                            direction = Direction.NORTH;
                        } else if (directionString.equals("EAST")) {
                            direction = Direction.EAST;
                        } else if (directionString.equals("SOUTH")) {
                            direction = Direction.SOUTH;
                        } else {
                            direction = Direction.WEST; //Why not set direction to west if it's unknown?
                        }
                        
                        tiles[i][line - 2] = new Tile(type, direction);
                    }
                }
                line++;
            }
            
            scanner.close(); 
            
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    //Function saveToFile creates a file and gives it information from the level class. Returns false if it can't save, returns true if it can save.
    public boolean saveToFile(String fileName) {
        String data = "";
    
        try {
            File file = new File(fileName); //Load file.
            file.createNewFile(); //try creating a new file.
            FileWriter fileWriter = new FileWriter(file); //Create a new FileWriter to write to the file.
            
            data = data + Integer.toString(this.width) + "x" +  Integer.toString(this.height) + "\n"; //Add the dimensions.
            data = data + this.levelName + "\n"; //Add the level name data.
            
            for(int y = 0; y < this.height; y++) { //Iterate tiles and add them to the save.
                for(int x = 0; x < this.height; x++) {
                    switch(this.tiles[x][y].type) { //Add the tile type.
                        case ROAD:
                            data += "ROAD,";
                            break;
                        case CURVED_ROAD:
                            data += "CURVED_ROAD,";
                            break;    
                        case GRASS:
                            data += "GRASS,";
                            break;  
                        default:
                            System.out.println("Something has gone bad");
                            return false;
                    }
                    
                    switch(this.tiles[x][y].direction) { //Add the direction.
                        case NORTH:
                            data += "NORTH,";
                            break;
                        case EAST:
                            data += "EAST,";
                            break;    
                        case SOUTH:
                            data += "SOUTH,";
                            break;  
                        default:
                            data += "WEST,";
                            break;
                    }
                }
                data = data + "\n"; //Add the newline seperator.
            }
            
            fileWriter.write(data);
            fileWriter.close();
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
}
