package Tiles;

/**
 * Class of Images
 *
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.24
 */

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;


public class Images {
    final BufferedImage roadNorthSouth;
    final BufferedImage roadEastWest;
    
    final BufferedImage curvedRoadWest;
    final BufferedImage curvedRoadEast;
    final BufferedImage curvedRoadNorth;
    final BufferedImage curvedRoadSouth;
    
    final BufferedImage houseNorth;
    final BufferedImage houseEast;
    final BufferedImage houseSouth;
    final BufferedImage houseWest;

    final BufferedImage grass;



    public Images() throws java.io.IOException {
        roadNorthSouth = ImageIO.read(new File("Assets/road_north_south.png"));
        roadEastWest   = ImageIO.read(new File("Assets/road_east_west.png"));
        
        curvedRoadWest  = ImageIO.read(new File("Assets/curved_road_west.png"));
        curvedRoadEast  = ImageIO.read(new File("Assets/curved_road_east.png"));
        curvedRoadNorth = ImageIO.read(new File("Assets/curved_road_north.png"));
        curvedRoadSouth = ImageIO.read(new File("Assets/curved_road_south.png"));
        
        houseNorth = ImageIO.read(new File("Assets/house_north.png"));
        houseEast  = ImageIO.read(new File("Assets/house_east.png"));
        houseSouth = ImageIO.read(new File("Assets/house_south.png"));
        houseWest  = ImageIO.read(new File("Assets/house_west.png"));
        
        grass = ImageIO.read(new File("Assets/grass.png"));  
    }
    
    public BufferedImage getImageFromType(TileType type, Direction direction) throws java.io.IOException {
        switch(type) {
            case ROAD:
                switch(direction) {
                    case NORTH:
                    case SOUTH: 
                        return roadNorthSouth;
                    case EAST:
                    case WEST: 
                        return roadEastWest;
                }
            case CURVED_ROAD: 
                switch(direction) {
                    case NORTH:
                        return curvedRoadNorth;
                    case SOUTH: 
                        return curvedRoadSouth;
                    case EAST:
                        return curvedRoadEast;
                    case WEST: 
                        return curvedRoadWest;
                }
            default:
            case GRASS:
                return grass;
        }
    }
    
    public BufferedImage getHouseImage( HouseType type,Direction direction) throws java.io.IOException {
        switch(direction) {
            case NORTH:
                return houseNorth;
            case SOUTH:      
                return houseSouth;
            case EAST:
                return houseEast;
            case WEST: 
                return houseWest;
        }
        
        return houseNorth;
    }
}

