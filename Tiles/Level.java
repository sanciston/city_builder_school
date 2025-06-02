package Tiles;
/**
 * Manages level loading and saving
 *
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.24
 */
import java.io.*;
import java.util.Scanner; 
import java.util.ArrayList;


public class Level {
    
    public Tile[][] tiles;
    public ArrayList<House> houses = new ArrayList<House>();
    
    public int width;
    public int height;
    
    public int maxLevelSize = 250;
    
    String levelName;
    
    public String loadedFile = "";
    
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
                        if(typeString.equals("0")) { 
                            type = TileType.GRASS;
                        } else if (typeString.equals("1")) {
                            type = TileType.ROAD;
                        } else if (typeString.equals("2")) {
                            type = TileType.CURVED_ROAD;
                        } else {
                            type = null;
                        }
                        
                        //Set the direction to corresponding enumerated value.
                        if(directionString.equals("0")) { 
                            direction = Direction.NORTH;
                        } else if (directionString.equals("1")) {
                            direction = Direction.EAST;
                        } else if (directionString.equals("2")) {
                            direction = Direction.SOUTH;
                        } else {
                            direction = Direction.WEST; //Why not set direction to west if it's unknown?
                        }
                        
                        tiles[i][line - 2] = new Tile(type, direction);
                    }
                } else {
                    String string = "";
                    HouseType type;
                    int charNumber = 0;
                    int storeys, x, y;
                    
                    //Get the storeys data up until the , seperator.
                    while(data.charAt(charNumber) != ','){
                        string = string + data.charAt(charNumber);
                        charNumber++;
                    }
                    
                    //Convert that data into intergers 
                    try {
                        storeys = Integer.parseInt(string);
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }


                    string = ""; //Reset the string.
                    charNumber++; //Skip past the , seperator.
                    
                    //Get the x data up until the , seperator.
                    while(data.charAt(charNumber) != ',') {
                        string = string + data.charAt(charNumber);
                        charNumber++;
                    }  
                    
                    try {
                        x = Integer.parseInt(string);
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                    
                    string = ""; //Reset the string.
                    charNumber++; //Skip past the , seperator.
                    
                    //Get the y data up until the , seperator.
                    while(data.charAt(charNumber) != ',') {
                        string = string + data.charAt(charNumber);
                        charNumber++;
                    }
                    
                    try {
                        y = Integer.parseInt(string);
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                    
                    string = ""; //Reset the string.
                    charNumber++; //Skip past the , seperator.
                    
                    //Get the type data up until the , seperator.
                    while(data.charAt(charNumber) != ',') {
                        string = string + data.charAt(charNumber);
                        charNumber++;
                    }
                    
                    if(string.equals("0")) {
                        type = HouseType.RESIDENTIAL;
                    } else if (string.equals("1")) {
                        type = HouseType.COMMERCIAL;
                    } else {
                        type = HouseType.BUSINESS;
                    }
                    
                    string = ""; //Reset the string.
                    charNumber++; //Skip past the , seperator.
                    
                    //Get the type data up until the , seperator.
                    while(data.charAt(charNumber) != ',') {
                        string = string + data.charAt(charNumber);
                        charNumber++;
                    }
                    
                    if(string.equals("0")) {
                        houses.add(new House(storeys, x, y, type, Direction.NORTH));
                    } else if (string.equals("1")) {
                        houses.add(new House(storeys, x, y, type, Direction.EAST));
                    } else if (string.equals("2")) {
                        houses.add(new House(storeys, x, y, type, Direction.SOUTH));
                    } else {
                        houses.add(new House(storeys, x, y, type, Direction.WEST));
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
            System.out.println(fileName);
            
            data = data + Integer.toString(this.width) + "x" +  Integer.toString(this.height) + "\n"; //Add the dimensions.
            data = data + this.levelName + "\n"; //Add the level name data.
            
            for(int y = 0; y < this.height; y++) { //Iterate tiles and add them to the save.
                for(int x = 0; x < this.height; x++) {
                    switch(this.tiles[x][y].type) { //Add the tile type.
                        case ROAD:
                            data += "1,";
                            break;
                        case CURVED_ROAD:
                            data += "2,";
                            break;    
                        case GRASS:
                            data += "0,";
                            break;  
                        default:
                            System.out.println("Something has gone bad");
                            return false;
                    }
                    
                    switch(this.tiles[x][y].direction) { //Add the direction.
                        case NORTH:
                            data += "0,";
                            break;
                        case EAST:
                            data += "1,";
                            break;    
                        case SOUTH:
                            data += "2,";
                            break;  
                        default:
                            data += "3,";
                            break;
                    }
                }
                data = data + "\n"; //Add the newline seperator.
            }
            
            for(int i = 0; i < houses.size(); i++) {
                data += Integer.toString(houses.get(i).storeys) + ",";
                data += Integer.toString(houses.get(i).x) + ",";
                data += Integer.toString(houses.get(i).y) + ",";
                
                switch(houses.get(i).type) {
                    case RESIDENTIAL:
                        data += "0,";
                        break;
                    case COMMERCIAL:
                        data += "1,";
                        break;
                    case BUSINESS:
                        data += "2,";
                        break;
                }
                switch(houses.get(i).direction) { //Add the direction.
                        case NORTH:
                            data += "0,";
                            break;
                        case EAST:
                            data += "1,";
                            break;    
                        case SOUTH:
                            data += "2,";
                            break;  
                        default:
                            data += "3,";
                            break;
                }
                data += "\n";
            }
            
            fileWriter.write(data);
            fileWriter.close();
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        
        loadedFile = fileName;
        return true;
    }
    
    public void newGame(String fileName, String levelName) {
        width = 100;
        height = 100;
        
        tiles = new Tile[width][height];
        this.levelName = levelName;
        
        for(int y = 0; y < this.height; y++) { //Iterate tiles and add them to the save.
            for(int x = 0; x < this.height; x++) {
                try {
                    tiles[x][y] = new Tile(TileType.GRASS, Direction.NORTH);
                } catch (java.io.IOException ioe) {
                    ioe.printStackTrace();
                }  
            }
        }
        
        saveToFile(fileName);
        loadedFile = fileName;
    }
}
