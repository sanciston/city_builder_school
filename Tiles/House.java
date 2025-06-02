package Tiles;
/**
 * The house class, for houses. 
 *
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.27
 */
public class House {
    public int storeys;
    public HouseType type;
    public Direction direction;
    
    public int x, y;
    
    public House(int storeys, int x, int y, HouseType type, Direction direction) {
        this.storeys = storeys; 
        this.x = x;
        this.y = y;
        this.type = type;
        this.direction = direction;
    }
}
